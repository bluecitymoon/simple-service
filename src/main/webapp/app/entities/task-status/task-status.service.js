(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('TaskStatus', TaskStatus);

    TaskStatus.$inject = ['$resource'];

    function TaskStatus ($resource) {
        var resourceUrl =  'api/task-statuses/:id';

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
