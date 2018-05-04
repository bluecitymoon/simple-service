(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerStatus', CustomerStatus);

    CustomerStatus.$inject = ['$resource'];

    function CustomerStatus ($resource) {
        var resourceUrl =  'api/customer-statuses/:id';

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
