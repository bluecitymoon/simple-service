(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('StudentAbsenceLog', StudentAbsenceLog);

    StudentAbsenceLog.$inject = ['$resource', 'DateUtils'];

    function StudentAbsenceLog ($resource, DateUtils) {
        var resourceUrl =  'api/student-absence-logs/:id';

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
