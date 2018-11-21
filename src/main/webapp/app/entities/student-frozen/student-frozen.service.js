(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('StudentFrozen', StudentFrozen);

    StudentFrozen.$inject = ['$resource', 'DateUtils'];

    function StudentFrozen ($resource, DateUtils) {
        var resourceUrl =  'api/student-frozens/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'generateStudentFrozen': {url: 'api/student-frozens/generate', method: 'POST'}
        });
    }
})();
