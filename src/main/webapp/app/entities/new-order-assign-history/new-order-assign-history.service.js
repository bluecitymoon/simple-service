(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('NewOrderAssignHistory', NewOrderAssignHistory);

    NewOrderAssignHistory.$inject = ['$resource', 'DateUtils'];

    function NewOrderAssignHistory ($resource, DateUtils) {
        var resourceUrl =  'api/new-order-assign-histories/:id';

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
