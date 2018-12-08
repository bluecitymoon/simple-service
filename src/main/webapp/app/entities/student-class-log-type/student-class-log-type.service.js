(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('StudentClassLogType', StudentClassLogType);

    StudentClassLogType.$inject = ['$resource'];

    function StudentClassLogType ($resource) {
        var resourceUrl =  'api/student-class-log-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
