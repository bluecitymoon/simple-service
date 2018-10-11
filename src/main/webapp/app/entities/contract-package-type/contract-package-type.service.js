(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ContractPackageType', ContractPackageType);

    ContractPackageType.$inject = ['$resource'];

    function ContractPackageType ($resource) {
        var resourceUrl =  'api/contract-package-types/:id';

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
