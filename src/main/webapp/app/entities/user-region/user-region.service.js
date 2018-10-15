(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('UserRegion', UserRegion);

    UserRegion.$inject = ['$resource', 'DateUtils'];

    function UserRegion ($resource, DateUtils) {
        var resourceUrl =  'api/user-regions/:id';

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
            'update': { method:'PUT' },
            'getAllUsersInRegion': {url: 'api/user-regions/users/:regionId', isArray: true}
        });
    }
})();
