'use strict';

/**
 * @ngdoc overview
 * @name roomChatApp
 * @description
 * # roomChatApp
 *
 * Main module of the application.
 */
angular
  .module('roomChatApp', [
    'ngAnimate',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar',
    'angularMoment',
    'vcRecaptcha'
  ])
  .constant('GRUNT_SERVE_URL', 'http://localhost:9000')
  .constant('TOMCAT_URL', 'http://localhost:8080')
  .constant('API_URI', '/api/v1')
  .factory('HOST_URL', function (GRUNT_SERVE_URL, TOMCAT_URL) {
    var originUrl = location.origin;
    var hostUrl = originUrl == GRUNT_SERVE_URL ? TOMCAT_URL : originUrl;
    return hostUrl;
  })
  .config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state({
        name: 'main',
        url: '/',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .state({
        name: 'about',
        url: '/about',
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .state({
        name: 'login',
        url: '/login',
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'login'
      })
      .state({
        name: 'sign-up',
        url: '/sign-up',
        templateUrl: 'views/sign-up.html',
        controller: 'SignUpCtrl',
        controllerAs: 'signUp'
      })
      .state({
        name: 'rooms',
        url: '/rooms',
        templateUrl: 'views/rooms.html',
        controller: 'RoomsCtrl',
        controllerAs: 'rooms'
      })
      .state({
        name: 'room',
        url: '/room/:roomId',
        templateUrl: 'views/room.html',
        controller: 'RoomCtrl',
        controllerAs: 'room'
      })
      .state({
        name: 'profile',
        url: '/profile',
        templateUrl: 'views/profile.html',
        controller: 'ProfileCtrl',
        controllerAs: 'profile'
      });

    $urlRouterProvider
      .otherwise('/');
  })
  .config(function ($httpProvider, MessageType) {
    $httpProvider.interceptors.push(function ($q, $injector) {
      return {
        request: function (config) {
          return config || $q.resolve(config);
        },
        requestError: function (request) {
          return $q.reject(request);
        },
        response: function (response) {
          return response || $q.resolve(response);
        },
        responseError: function (response) {
          console.log(response);
          var messageBox = $injector.get('messageBox');
          if (response.data) {
            if (response.data.error_description === 'Bad credentials')
              messageBox.show('Wrong username or password', MessageType.ERROR);
            else if (response.data.error_description === 'User is disabled')
              messageBox.show('Account is disabled', MessageType.ERROR);
            else if (response.data.error_description === 'User account is locked')
              messageBox.show('Account is locked', MessageType.ERROR);
            else if (response.data.error === 'invalid_token')
              $injector.get('oauth2').handleInvalidToken();
            else if (response.data.errorCode === 401)
              messageBox.show(response.data.message, MessageType.ERROR).then(
                function () {
                  $injector.get('oauth2').handleInvalidToken();
                }
              );
            else if (response.data.message)
              messageBox.show(response.data.message, MessageType.ERROR);
            else if (response.data.description)
              messageBox.show("Unexpected error. Error description: " + response.data.description, MessageType.ERROR);
          }
          else
            messageBox.show(response, MessageType.ERROR);
          return $q.reject(response);
        }
      };
    });
  })
  .run(function ($rootScope,
                 $q,
                 oauth2) {
    oauth2.updateAuthoritiesCallback(function (authorities) {
      $rootScope.hasUserAuthority = authorities.indexOf('ROLE_USER') > -1;
      $rootScope.hasAdminAuthority = authorities.indexOf('ROLE_ADMIN') > -1;
      $rootScope.isAnnonymos = authorities.indexOf('ROLE_ANONYMOUS') > -1;
    });
    oauth2.updateAuthorities();
  });
