(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('Customer', Customer);

    Customer.$inject = ['$resource', 'DateUtils'];

    function Customer ($resource, DateUtils) {
        var resourceUrl =  'api/customers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'queryWithLog': { url: 'api/customers/withlog', method: 'GET', isArray: true},
            'queryByKeyword': { url: 'api/customers/search/:keyword', method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                        data.birthday = DateUtils.convertDateTimeFromServer(data.birthday);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'batchUpdate' : {
                method: 'PUT',
                url: 'api/customers/batchupdate',
                isArray: true
            },
            'batchAssignCourseConsultant' : {
                method: 'PUT',
                url: 'api/customers/batch-assign-course-consultant',
                isArray: true
            },
            'getOverview' : {
                method: 'GET',
                url: 'api/customers/overview'
            },
            'preloadMergedCustomer': {
                url: 'api/customers/premerge/:oid/:tid', method: 'GET'
            },
            'mergeCustomer': {
                url: 'api/customers/merge', method: 'POST'
            },
            'getVistedCustomerStatusReport': {url: 'api/customers/visited/status/report', method: 'POST', isArray: true}
        });
    }
})();
