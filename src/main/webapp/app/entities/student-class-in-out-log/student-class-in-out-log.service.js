(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('StudentClassInOutLog', StudentClassInOutLog);

    StudentClassInOutLog.$inject = ['$resource', 'DateUtils'];

    function StudentClassInOutLog ($resource, DateUtils) {
        var resourceUrl =  'api/student-class-in-out-logs/:id';

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
