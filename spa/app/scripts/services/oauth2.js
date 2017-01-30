'use strict';

/**
 * @ngdoc service
 * @name roomChatApp.oauth2
 * @description
 * # oauth2
 * Factory in the roomChatApp.
 */
angular.module('roomChatApp')
  .factory('oauth2', function ($http, $cookies, $httpParamSerializer, $q, $state, HOST_URL, UserAuthorities) {
    var accessTokenData = {grant_type: "password"};
    var encoded = btoa("webapp:123456");

    if ($cookies.get("access_token"))
      $http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get("access_token");

    var logInReq = {
      method: 'POST',
      url: HOST_URL + "/oauth/token",
      headers: {
        "Authorization": "Basic " + encoded,
        "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
      }
    };

    var logOutReq = {
      method: 'DELETE',
      url: HOST_URL + "/oauth/revoke-token"
    };

    var updateCallback;
    var updateAuthorities = function () {
      UserAuthorities.query(
        function (data) {
          updateCallback(data);
        }
      );
    };

    return {
      logIn: function (username, password) {
        accessTokenData.username = username;
        accessTokenData.password = password;

        logInReq.data = $httpParamSerializer(accessTokenData);

        var deferred = $q.defer();
        $http(logInReq).then(function (data) {
          $http.defaults.headers.common.Authorization = 'Bearer ' + data.data.access_token;
          $cookies.put("access_token", data.data.access_token);

          updateAuthorities();
          deferred.resolve();
        }, function (error) {
          deferred.reject(error);
        });

        return deferred.promise;
      },
      logOut: function () {
        delete $http.defaults.headers.common.Authorization;
        $cookies.remove("access_token");
        updateAuthorities();
      },
      handleInvalidToken: function () {
        $cookies.remove("access_token");
        delete $http.defaults.headers.common.Authorization;
        location.href = location.origin;
      },
      updateAuthorities: function () {
        updateAuthorities();
      },
      updateAuthoritiesCallback: function (callback) {
        updateCallback = callback;
      }
    };
  });
