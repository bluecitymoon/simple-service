(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ClassCategory', ClassCategory);

    ClassCategory.$inject = ['$resource'];

    function ClassCategory ($resource) {
        var resourceUrl =  'api/class-categories/:id';

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
