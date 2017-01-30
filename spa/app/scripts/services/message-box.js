'use strict';

/**
 * @ngdoc service
 * @name roomChatApp.messageBox
 * @description
 * # messageBox
 * Factory in the roomChatApp.
 */
angular.module('roomChatApp')
  .factory('messageBox', function ($uibModal, $q, MessageType) {
    var messageContent = "none";
    var messageTitle = "none";
    var panelClass = "panel-default";
    var messagefieldErrors;

    var messageModalOptions = {
      animation: true,
      size: "lg",
      resolve: {
        content: function () {
          return messageContent;
        },
        title: function () {
          return messageTitle;
        },
        panelClass: function () {
          return panelClass;
        }
      }
    };

    var validationErrorMessageModalOptions = {
      animation: true,
      size: "lg",
      resolve: {
        fieldErrors: function () {
          return messagefieldErrors;
        }
      }
    };

    var getTitle = function (messageType) {
      if (messageType === MessageType.INFO || messageType === MessageType.SUCCESS)
        return "Info";
      if (messageType === MessageType.WARNING)
        return "Warning";
      if (messageType === MessageType.ERROR)
        return "Error";
    };

    var getPanelClass = function (messageType) {
      if (messageType === MessageType.INFO)
        return 'panel-info';
      if (messageType === MessageType.SUCCESS)
        return 'panel-success';
      if (messageType === MessageType.WARNING)
        return 'panel-warning';
      if (messageType === MessageType.ERROR)
        return 'panel-danger';
    };

    return {
      show: function (content, messageType) {
        messageContent = content;
        messageType = messageType || MessageType.SUCCESS;
        messageTitle = getTitle(messageType);
        panelClass = getPanelClass(messageType);

        messageModalOptions.templateUrl = 'views/modal-message.html';
        messageModalOptions.controller = 'ModalMessageCtrl';
        return $uibModal.open(messageModalOptions).result;
      },
      showGeneralQuestion: function (content, messageType) {
        messageContent = content;
        messageType = messageType || MessageType.WARNING;
        panelClass = getPanelClass(messageType);
        messageTitle = messageTitle = getTitle(messageType);
        messageModalOptions.templateUrl = 'views/general-question-modal.html';
        messageModalOptions.controller = 'GeneralQuestionModalCtrl';
        return $uibModal.open(messageModalOptions).result;
      }
    };
  })
  .constant('MessageType',
    {
      INFO: 1,
      SUCCESS: 2,
      WARNING: 3,
      ERROR: 4
    }
  );
