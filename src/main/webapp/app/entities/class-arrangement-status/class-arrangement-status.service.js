(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ClassArrangementStatus', ClassArrangementStatus);

    ClassArrangementStatus.$inject = ['$resource'];

    function ClassArrangementStatus ($resource) {
        var resourceUrl =  'api/class-arrangement-statuses/:id';

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
