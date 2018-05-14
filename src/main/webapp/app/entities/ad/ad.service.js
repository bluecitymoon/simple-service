(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('Ad', Ad);

    Ad.$inject = ['$resource'];

    function Ad ($resource) {
        var resourceUrl =  'api/ads/:id';

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
