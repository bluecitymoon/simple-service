(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('FreeClassRecord', FreeClassRecord);

    FreeClassRecord.$inject = ['$resource'];

    function FreeClassRecord ($resource) {
        var resourceUrl =  'api/free-class-records/:id';

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
