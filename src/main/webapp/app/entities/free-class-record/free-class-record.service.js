(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('FreeClassRecord', FreeClassRecord);

    FreeClassRecord.$inject = ['$resource', 'DateUtils'];

    function FreeClassRecord ($resource, DateUtils) {
        var resourceUrl =  'api/free-class-records/:id';

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
            'connectCustomer' : {
                method: 'GET',
                url: 'api/customers/connect/:id',
                params: { id : '@id'}
            },
            'batchUpdate' : {
                method: 'PUT',
                url: 'api/free-class-records/batchupdate',
                isArray: true
            },
            'update': { method:'PUT' }
        });
    }
})();
