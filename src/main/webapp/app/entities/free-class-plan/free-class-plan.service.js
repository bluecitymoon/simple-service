(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('FreeClassPlan', FreeClassPlan);

    FreeClassPlan.$inject = ['$resource', 'DateUtils'];

    function FreeClassPlan ($resource, DateUtils) {
        var resourceUrl =  'api/free-class-plans/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.planDate = DateUtils.convertDateTimeFromServer(data.planDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
