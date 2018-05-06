(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('EducationLevel', EducationLevel);

    EducationLevel.$inject = ['$resource'];

    function EducationLevel ($resource) {
        var resourceUrl =  'api/education-levels/:id';

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
