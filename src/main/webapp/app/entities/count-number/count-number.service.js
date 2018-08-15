(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CountNumber', CountNumber);

    CountNumber.$inject = ['$resource'];

    function CountNumber ($resource) {
        var resourceUrl =  'api/count-numbers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'getByLoopWay': {url: 'api/count-numbers/loop-way/:id', method: 'GET', isArray: true},
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
