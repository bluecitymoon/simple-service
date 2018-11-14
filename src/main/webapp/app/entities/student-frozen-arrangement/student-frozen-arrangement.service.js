(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('StudentFrozenArrangement', StudentFrozenArrangement);

    StudentFrozenArrangement.$inject = ['$resource', 'DateUtils'];

    function StudentFrozenArrangement ($resource, DateUtils) {
        var resourceUrl =  'api/student-frozen-arrangements/:id';

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
