(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerCommunicationLog', CustomerCommunicationLog);

    CustomerCommunicationLog.$inject = ['$resource', 'DateUtils'];

    function CustomerCommunicationLog ($resource, DateUtils) {
        var resourceUrl =  'api/customer-communication-logs/:id';

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
