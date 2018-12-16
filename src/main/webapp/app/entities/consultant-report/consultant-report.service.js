(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ConsultantReport', ConsultantReport);

    ConsultantReport.$inject = ['$resource', 'DateUtils'];

    function ConsultantReport ($resource, DateUtils) {
        var resourceUrl =  'api/consultant-reports/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.weekFromDate = DateUtils.convertDateTimeFromServer(data.weekFromDate);
                        data.weekEndDate = DateUtils.convertDateTimeFromServer(data.weekEndDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
