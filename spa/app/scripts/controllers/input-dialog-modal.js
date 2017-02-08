'use strict';

/**
 * @ngdoc function
 * @name roomChatApp.controller:InputDialogModalCtrl
 * @description
 * # InputDialogModalCtrl
 * Controller of the roomChatApp
 */
angular.module('roomChatApp')
  .controller('InputDialogModalCtrl', function ($scope, $uibModalInstance, content, title, panelClass) {
    $scope.title = title;
    $scope.panelClass = panelClass;
    $scope.value = content;

    $scope.onSubmit = function () {
      $uibModalInstance.close($scope.value);
    };
    $scope.onCancel = function () {
      $uibModalInstance.dismiss();
    };
  });
