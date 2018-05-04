(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerScheduleStatus', CustomerScheduleStatus);

    CustomerScheduleStatus.$inject = ['$resource'];

    function CustomerScheduleStatus ($resource) {
        var resourceUrl =  'api/customer-schedule-statuses/:id';

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
