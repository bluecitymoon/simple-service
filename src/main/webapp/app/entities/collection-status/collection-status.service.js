(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CollectionStatus', CollectionStatus);

    CollectionStatus.$inject = ['$resource'];

    function CollectionStatus ($resource) {
        var resourceUrl =  'api/collection-statuses/:id';

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
