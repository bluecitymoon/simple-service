(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ContractNature', ContractNature);

    ContractNature.$inject = ['$resource'];

    function ContractNature ($resource) {
        var resourceUrl =  'api/contract-natures/:id';

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
