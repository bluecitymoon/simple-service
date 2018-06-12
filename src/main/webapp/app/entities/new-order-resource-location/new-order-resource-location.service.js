(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('NewOrderResourceLocation', NewOrderResourceLocation);

    NewOrderResourceLocation.$inject = ['$resource'];

    function NewOrderResourceLocation ($resource) {
        var resourceUrl =  'api/new-order-resource-locations/:id';

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
