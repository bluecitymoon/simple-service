(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('VistedCustomerStatus', VistedCustomerStatus);

    VistedCustomerStatus.$inject = ['$resource'];

    function VistedCustomerStatus ($resource) {
        var resourceUrl =  'api/visted-customer-statuses/:id';

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
