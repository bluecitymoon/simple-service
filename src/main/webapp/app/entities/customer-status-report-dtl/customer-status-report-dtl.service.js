(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerStatusReportDtl', CustomerStatusReportDtl);

    CustomerStatusReportDtl.$inject = ['$resource'];

    function CustomerStatusReportDtl ($resource) {
        var resourceUrl =  'api/customer-status-report-dtls/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'getStatusReport': { url: 'api/customers/status/report', method: 'POST'},
            'getLocationStatusReport': { url: 'api/customers/status/report/location', method: 'POST', isArray: true},
            'loadReferReport': { url: 'api/customers/status/report/refer', method: 'POST', isArray: true},
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
