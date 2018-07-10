(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ContractStatus', ContractStatus);

    ContractStatus.$inject = ['$resource'];

    function ContractStatus ($resource) {
        var resourceUrl =  'api/contract-statuses/:id';

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
