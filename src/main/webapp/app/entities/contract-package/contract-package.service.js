(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ContractPackage', ContractPackage);

    ContractPackage.$inject = ['$resource'];

    function ContractPackage ($resource) {
        var resourceUrl =  'api/contract-packages/:id';

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
