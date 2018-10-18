(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .factory('authInterceptor', authInterceptor);

    authInterceptor.$inject = ['$rootScope', '$q', '$location', '$localStorage', '$sessionStorage'];

    function authInterceptor ($rootScope, $q, $location, $localStorage, $sessionStorage) {
        var service = {
            request: request
        };

        return service;

        function request (config) {
            /*jshint camelcase: false */
            config.headers = config.headers || {};
            var token = $localStorage.authenticationToken || $sessionStorage.authenticationToken;
            var regionId = $localStorage.regionId || $sessionStorage.regionId;
            if (token && token.access_token) {
                config.headers.Authorization = 'Bearer ' + token.access_token;

                if ($localStorage.currentUserRegion) {
                    config.headers["X-RegionId"] = $localStorage.currentUserRegion.id

                }
            }
            return config;
        }
    }
})();
