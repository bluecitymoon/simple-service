(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .factory('User', User);

    User.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'},
            'getAllPwis': {
                url: "api/users/role/ROLE_PWI",
                method: 'GET',
                isArray: true
            },
            'getAllPwiWithOuterUser': {
                url: "api/users/get-all-pwis-with-outer-users",
                method: 'GET',
                isArray: true
            },
            'getAllSales': {
                url: "api/users/role/ROLE_SALES",
                method: 'GET',
                isArray: true
            },
            'getAllCourseConsultant': {
                url: "api/users/role/ROLE_COURSE_CONSULTANT",
                method: 'GET',
                isArray: true
            },
            'getDetailedAuthorities': {
                url: 'api/users/authorities/detail',
                method: 'GET',
                isArray: true
            },
            'resetPasswordForUser': {
                url: 'api/users/force-reset-password',
                method: 'POST'
            }
        });

        return service;
    }
})();
