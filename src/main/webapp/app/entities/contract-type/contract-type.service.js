(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ContractType', ContractType);

    ContractType.$inject = ['$resource'];

    function ContractType ($resource) {
        var resourceUrl =  'api/contract-types/:id';

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
