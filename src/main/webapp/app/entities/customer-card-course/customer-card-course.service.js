(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerCardCourse', CustomerCardCourse);

    CustomerCardCourse.$inject = ['$resource'];

    function CustomerCardCourse ($resource) {
        var resourceUrl =  'api/customer-card-courses/:id';

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
