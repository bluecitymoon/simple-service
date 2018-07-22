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
                        data.actuallMeetDate = DateUtils.convertDateTimeFromServer(data.actuallMeetDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'batchUpdate' : {
                method: 'PUT',
                url: 'api/customer-communication-schedules/batchUpdate',
                isArray: true
            },
            'sign': { method:'POST', url: 'api/customer-communication-schedules/signin/:id', params: { id : '@id'} },
            'customersignin': { method:'POST', url: 'api/customer-communication-schedules/customersignin/:id', params: { id : '@id'} },
            'getSchedulesToday': { url: 'api/customer-communication-schedules/today', method: 'GET', isArray: true}
        });
    }
})();
