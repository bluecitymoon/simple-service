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
            'update': { method:'PUT' },
            "getCustomerTrackTasks": {
                url: "api/customer-track-tasks/customer/:cid",
                method: 'GET',
                isArray: true
            },
            "closeTask": {
                url: "api/customer-track-tasks/close/:id",
                method: 'GET'
            }
        });
    }
})();
