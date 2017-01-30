'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:HeaderCtrl
 * @description
 * # HeaderCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('HeaderCtrl', function ($scope, $state, oauth2) {
    $scope.logOut = function () {
      oauth2.logOut();
      $state.go('main');
    }
  });
