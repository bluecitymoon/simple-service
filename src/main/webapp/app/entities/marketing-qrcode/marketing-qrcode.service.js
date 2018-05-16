(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('MarketingQrcode', MarketingQrcode);

    MarketingQrcode.$inject = ['$resource', 'DateUtils'];

    function MarketingQrcode ($resource, DateUtils) {
        var resourceUrl =  'api/marketing-qrcodes/:id';

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
            'generate': { url: 'api/marketing-qrcodes/generate/:id', method: 'POST'}
        });
    }
})();
