'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('LoginCtrl', function ($scope, $state, oauth2) {
    $scope.logIn = function () {
      oauth2.logIn($scope.username, $scope.password).then(
        function () {
          $state.go('rooms');
        }
      );
    };
  });
