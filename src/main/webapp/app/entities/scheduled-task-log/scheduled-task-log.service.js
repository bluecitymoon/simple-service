(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ScheduledTaskLog', ScheduledTaskLog);

    ScheduledTaskLog.$inject = ['$resource', 'DateUtils'];

    function ScheduledTaskLog ($resource, DateUtils) {
        var resourceUrl =  'api/scheduled-task-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
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
