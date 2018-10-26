(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('UserGuideDocument', UserGuideDocument);

    UserGuideDocument.$inject = ['$resource', 'DateUtils'];

    function UserGuideDocument ($resource, DateUtils) {
        var resourceUrl =  'api/user-guide-documents/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
