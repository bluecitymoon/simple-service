(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('MarketingNewOrderPlan', MarketingNewOrderPlan);

    MarketingNewOrderPlan.$inject = ['$resource', 'DateUtils'];

    function MarketingNewOrderPlan ($resource, DateUtils) {
        var resourceUrl =  'api/marketing-new-order-plans/:id';

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
