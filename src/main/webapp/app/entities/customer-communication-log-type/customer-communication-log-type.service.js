(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerCommunicationLogType', CustomerCommunicationLogType);

    CustomerCommunicationLogType.$inject = ['$resource'];

    function CustomerCommunicationLogType ($resource) {
        var resourceUrl =  'api/customer-communication-log-types/:id';

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
