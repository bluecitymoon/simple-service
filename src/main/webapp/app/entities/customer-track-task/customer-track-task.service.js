(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerTrackTask', CustomerTrackTask);

    CustomerTrackTask.$inject = ['$resource'];

    function CustomerTrackTask ($resource) {
        var resourceUrl =  'api/customer-track-tasks/:id';

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
