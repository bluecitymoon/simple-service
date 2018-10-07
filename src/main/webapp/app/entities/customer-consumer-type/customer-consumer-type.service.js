(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerConsumerType', CustomerConsumerType);

    CustomerConsumerType.$inject = ['$resource'];

    function CustomerConsumerType ($resource) {
        var resourceUrl =  'api/customer-consumer-types/:id';

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
