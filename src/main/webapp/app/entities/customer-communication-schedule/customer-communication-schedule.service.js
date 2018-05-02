(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerCommunicationSchedule', CustomerCommunicationSchedule);

    CustomerCommunicationSchedule.$inject = ['$resource', 'DateUtils'];

    function CustomerCommunicationSchedule ($resource, DateUtils) {
        var resourceUrl =  'api/customer-communication-schedules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.sceduleDate = DateUtils.convertDateTimeFromServer(data.sceduleDate);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
