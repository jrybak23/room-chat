'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:SignUpCtrl
 * @description
 * # SignUpCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('SignUpCtrl', function ($scope, $state, User, messageBox) {
    $scope.user = {};

    $scope.submitSignUp = function () {
      User.save({},
        $scope.user,
        function () {
          messageBox.show("Successfully registered");
          $state.go('login');
        });
    }
  });
