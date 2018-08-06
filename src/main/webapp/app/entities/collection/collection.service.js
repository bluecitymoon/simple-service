(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('Collection', Collection);

    Collection.$inject = ['$resource', 'DateUtils'];

    function Collection ($resource, DateUtils) {
        var resourceUrl =  'api/collections/:id';

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
            'confirm': {
                method: 'POST',
                url: 'api/collections/confirm'
            }
        });
    }
})();
