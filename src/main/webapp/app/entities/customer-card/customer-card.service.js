(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerCard', CustomerCard);

    CustomerCard.$inject = ['$resource', 'DateUtils'];

    function CustomerCard ($resource, DateUtils) {
        var resourceUrl =  'api/customer-cards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.signDate = DateUtils.convertDateTimeFromServer(data.signDate);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'getSequenceNumber': {method: 'POST', url: 'api/sequence/get'},
            'generateCustomerCardNumber': {method: 'POST', url: 'api/customer-cards/generate-card-number'}
        });
    }
})();
