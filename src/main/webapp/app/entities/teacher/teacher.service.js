(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('Teacher', Teacher);

    Teacher.$inject = ['$resource'];

    function Teacher ($resource) {
        var resourceUrl =  'api/teachers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
