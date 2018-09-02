(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerScheduleFeedback', CustomerScheduleFeedback);

    CustomerScheduleFeedback.$inject = ['$resource', 'DateUtils'];

    function CustomerScheduleFeedback ($resource, DateUtils) {
        var resourceUrl =  'api/customer-schedule-feedbacks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
