'use strict';

/**
 * @ngdoc service
 * @name roomChatApp.chat
 * @description
 * # chat
 * Factory in the roomChatApp.
 */
angular.module('roomChatApp')
  .factory('chat', function ($q, $http, HOST_URL) {
    var stompClient = null, room;
    return {
      connect: function (roomId, messageHandlerCallback, errorHandlerCallBack) {
        room = roomId;
        var deferred = $q.defer();
        var socket = new SockJS(HOST_URL + '/chat/rooms/' + roomId);
        stompClient = Stomp.over(socket);
        stompClient.debug = null;
        var headers = {};
        headers['Authorization'] = $http.defaults.headers.common.Authorization;
        stompClient.connect(headers,
          function (frame) {
            deferred.resolve(frame);
            stompClient.subscribe('/topic/rooms/' + roomId,
              function (messageOutput) {
                var message = JSON.parse(messageOutput.body);
                messageHandlerCallback(message);
              }
            );
            stompClient.subscribe('/user/queue/error',
              function (messageOutput) {
                var message = JSON.parse(messageOutput.body);
                errorHandlerCallBack(message);
              }
            );
          },
          function (frame) {
            deferred.reject(frame);
          }
        );

        return deferred.promise;
      },
      disconnect: function () {
        stompClient.disconnect();
      },
      sendMessage: function (message) {
        stompClient.send("/chat/rooms/" + room, {}, JSON.stringify(message));
      }
    };
  });
