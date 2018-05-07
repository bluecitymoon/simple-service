(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ClassArrangementRule', ClassArrangementRule);

    ClassArrangementRule.$inject = ['$resource', 'DateUtils'];

    function ClassArrangementRule ($resource, DateUtils) {
        var resourceUrl =  'api/class-arrangement-rules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.estimateStartDate = DateUtils.convertDateTimeFromServer(data.estimateStartDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
