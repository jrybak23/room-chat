'use strict';

/**
 * @ngdoc service
 * @name roomChatApp.room
 * @description
 * # room
 * Factory in the roomChatApp.
 */
angular.module('roomChatApp')
  .factory('MyRoom', function ($resource, HOST_URL, API_URI) {
    return $resource(HOST_URL + API_URI + "/users/me/rooms/:roomId",
      {roomId: '@id'},
      {update: {method: 'PUT'}}
    );
  });
