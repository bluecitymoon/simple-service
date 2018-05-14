(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('SystemVariable', SystemVariable);

    SystemVariable.$inject = ['$resource'];

    function SystemVariable ($resource) {
        var resourceUrl =  'api/system-variables/:id';

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
