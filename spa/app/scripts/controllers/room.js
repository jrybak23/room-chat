'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:RoomCtrl
 * @description
 * # RoomCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('RoomCtrl', function ($scope, $stateParams, chat) {
    $scope.roomId = $stateParams.roomId;
    $scope.messages = [];

    var messageHandler = function (message) {
      $scope.$apply(function () {
        $scope.messages.push(message);
      });
      console.log($scope.messages);
    };

    chat.connect($stateParams.roomId, messageHandler).then(
      function (frame) {
        //  console.log("Connected:" + frame);
      },
      function (frame) {
        //console.log("Failed:" + frame);
      }
    );

    $scope.sendMessage = function () {
      chat.sendMessage({content: $scope.message});
      $scope.message = "";
    }
  });
