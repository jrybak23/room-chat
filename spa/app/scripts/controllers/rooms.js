'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:RoomsCtrl
 * @description
 * # RoomsCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('RoomsCtrl', function ($scope, $state, MyRoom, messageBox) {
    $scope.getRooms = function () {
      MyRoom.query(
        function (response) {
          $scope.rooms = response;
        }
      );
    };

    $scope.goRoom = function (room) {
      $state.go('room', {roomId: room.id});
    };

    $scope.createRoom = function () {

      messageBox.showInputDialog('Input name of room', 'fooroom').then(
        function (name) {
          MyRoom.save(
            {name: name},
            function () {
              $scope.getRooms();
            }
          );
        }
      );

    };

    $scope.deleteRoom = function (room) {
      messageBox.showGeneralQuestion("Do you really want to delete this room?").then(
        function () {
          MyRoom.delete(
            {roomId: room.id},
            function () {
              $scope.getRooms();
            }
          );
        }
      );
    };

    $scope.joinToRoom = function () {
      $state.go("room", {roomId: $scope.roomId});
    };

    $scope.getRooms();
  });
