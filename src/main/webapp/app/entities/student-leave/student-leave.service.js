(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('StudentLeave', StudentLeave);

    StudentLeave.$inject = ['$resource', 'DateUtils'];

    function StudentLeave ($resource, DateUtils) {
        var resourceUrl =  'api/student-leaves/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.calculateStartDate = DateUtils.convertDateTimeFromServer(data.calculateStartDate);
                        data.calculateEndDate = DateUtils.convertDateTimeFromServer(data.calculateEndDate);
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
