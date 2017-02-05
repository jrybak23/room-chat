'use strict';

/**
 * @ngdoc service
 * @name roomChatApp.user
 * @description
 * # user
 * Factory in the roomChatApp.
 */
angular.module('roomChatApp')
  .factory('User', function ($resource, HOST_URL, API_URI) {
    return $resource(HOST_URL + API_URI + "/users/:userId", {userId: '@id'});
  });
