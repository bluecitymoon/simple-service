(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerCardType', CustomerCardType);

    CustomerCardType.$inject = ['$resource'];

    function CustomerCardType ($resource) {
        var resourceUrl =  'api/customer-card-types/:id';

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
