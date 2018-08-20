(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('StudentClassLog', StudentClassLog);

    StudentClassLog.$inject = ['$resource', 'DateUtils'];

    function StudentClassLog ($resource, DateUtils) {
        var resourceUrl =  'api/student-class-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.actualTakenDate = DateUtils.convertDateTimeFromServer(data.actualTakenDate);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'batchSignin': {method: 'POST', url: 'api/student-class-logs/batch-sign-in'}
        });
    }
})();
