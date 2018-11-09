(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ClassCategoryBase', ClassCategoryBase);

    ClassCategoryBase.$inject = ['$resource'];

    function ClassCategoryBase ($resource) {
        var resourceUrl =  'api/class-category-bases/:id';

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
