(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('AuthorityUserGuideDocument', AuthorityUserGuideDocument);

    AuthorityUserGuideDocument.$inject = ['$resource', 'DateUtils'];

    function AuthorityUserGuideDocument ($resource, DateUtils) {
        var resourceUrl =  'api/authority-user-guide-documents/:id';

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
