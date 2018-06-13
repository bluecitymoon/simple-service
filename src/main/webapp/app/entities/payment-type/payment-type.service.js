(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('PaymentType', PaymentType);

    PaymentType.$inject = ['$resource'];

    function PaymentType ($resource) {
        var resourceUrl =  'api/payment-types/:id';

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
