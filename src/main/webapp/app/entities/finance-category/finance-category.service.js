(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('FinanceCategory', FinanceCategory);

    FinanceCategory.$inject = ['$resource'];

    function FinanceCategory ($resource) {
        var resourceUrl =  'api/finance-categories/:id';

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
