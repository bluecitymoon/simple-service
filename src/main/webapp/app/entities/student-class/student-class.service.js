(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('StudentClass', StudentClass);

    StudentClass.$inject = ['$resource', 'DateUtils'];

    function StudentClass ($resource, DateUtils) {
        var resourceUrl =  'api/student-classes/:id';

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
            'update': { method:'PUT' },
            'batchAssignStudentIntoClass' : { url: 'api/student-classes/batch-assign', method:'POST' },
            'createSingleStudentClass': { url: 'api/student-classes/single-assign', method:'POST' },
            'getAllStudentInClass': { url:'api/student-classes/students/:classId', method: 'GET', isArray: true}
        });
    }
})();
