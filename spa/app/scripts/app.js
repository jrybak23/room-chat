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
    'angular-loading-bar'
  ])
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
      });

    $urlRouterProvider
      .otherwise('/');
  })
  .config(function ($httpProvider) {
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
          return $q.reject(response);
        }
      };
    });
  });
