'use strict';

/**
 * @ngdoc service
 * @name roomChatApp.UserAuthorities
 * @description
 * # UserAuthorities
 * Factory in the roomChatApp.
 */
angular.module('roomChatApp')
  .factory('UserAuthorities', function ($resource, HOST_URL, API_URI) {
    return $resource(HOST_URL + API_URI + "/users/me/authorities");
  });
