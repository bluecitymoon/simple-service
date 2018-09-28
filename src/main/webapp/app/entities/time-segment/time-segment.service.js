(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('TimeSegment', TimeSegment);

    TimeSegment.$inject = ['$resource'];

    function TimeSegment ($resource) {
        var resourceUrl =  'api/time-segments/:id';

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
