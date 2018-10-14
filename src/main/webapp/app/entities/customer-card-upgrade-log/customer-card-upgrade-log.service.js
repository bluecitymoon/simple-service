(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('CustomerCardUpgradeLog', CustomerCardUpgradeLog);

    CustomerCardUpgradeLog.$inject = ['$resource', 'DateUtils'];

    function CustomerCardUpgradeLog ($resource, DateUtils) {
        var resourceUrl =  'api/customer-card-upgrade-logs/:id';

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
