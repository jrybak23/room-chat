'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:ProfileCtrl
 * @description
 * # ProfileCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('ProfileCtrl', function ($scope, User) {
    User.get({userId: 'me'}).$promise.then(
      function (response) {
        $scope.user = response;
      }
    );
  });
