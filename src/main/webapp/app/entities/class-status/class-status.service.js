(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ClassStatus', ClassStatus);

    ClassStatus.$inject = ['$resource'];

    function ClassStatus ($resource) {
        var resourceUrl =  'api/class-statuses/:id';

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
