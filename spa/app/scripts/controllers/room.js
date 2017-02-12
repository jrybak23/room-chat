'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:RoomCtrl
 * @description
 * # RoomCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('RoomCtrl', function ($scope, $stateParams, $state, chat, MyRoom, Messages, messageBox, MessageType) {
    $scope.roomId = $stateParams.roomId;
    $scope.messages = [];
    $scope.currentPage = 0;
    $scope.totalPages = 1;
    var receivedMessages = 0;

    $scope.getRoom = function () {
      MyRoom.get({roomId: $stateParams.roomId},
        function (response) {
          $scope.room = response;
          initWebSocket();
          $scope.loadMessages();
        },
        function (error) {
          if (error.data.errorCode === 404)
            messageBox.show('No room with id' + $stateParams.roomId, MessageType.ERROR).then(
              function () {
                $state.go('rooms');
              },
              function () {
                $state.go('rooms');
              }
            );
        });
    };

    var initWebSocket = function () {
      var messageHandler = function (message) {
        $scope.$apply(function () {
          $scope.messages.push(message);
        });
        receivedMessages++;
      };

      var errorHandler = function (error) {
        console.error("Error:", error);
      };

      chat.connect($stateParams.roomId, messageHandler, errorHandler).then(
        function (frame) {
          console.log("Connected:", frame);
        },
        function (frame) {
          console.error("Failed:", frame);
        }
      );

      $scope.sendMessage = function () {
        chat.sendMessage({content: $scope.message});
        $scope.message = "";
      };
    };

    $scope.updateRoom = function () {
      MyRoom.update(
        {roomId: $stateParams.roomId},
        $scope.room
      );
    };

    $scope.loadMessages = function () {
      Messages.get(
        {
          roomId: $stateParams.roomId,
          page: $scope.currentPage++,
          size: 5,
          offset: receivedMessages
        },
        function (response) {
          $scope.totalPages = response.totalPages;
          $scope.messages = response.content.reverse().concat($scope.messages);
        }
      );
    };

    $scope.getRoom();
  });
