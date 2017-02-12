'use strict';

/**
 * @ngdoc service
 * @name roomChatApp.messages
 * @description
 * # messages
 * Factory in the roomChatApp.
 */
angular.module('roomChatApp')
  .factory('Messages', function ($resource, HOST_URL, API_URI) {
    return $resource(HOST_URL + API_URI + "/rooms/:roomId/messages", {roomId: '@id'});
  });
