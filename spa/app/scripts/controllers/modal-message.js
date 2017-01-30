'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:ModalMessageCtrl
 * @description
 * # ModalMessageCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('ModalMessageCtrl', function ($scope, $uibModalInstance, content, title, panelClass) {
    $scope.content = content;
    $scope.title = title;
    $scope.panelClass = panelClass;

    $scope.onOk = function () {
      $uibModalInstance.close();
    };
  });
