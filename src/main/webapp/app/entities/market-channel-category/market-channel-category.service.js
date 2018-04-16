(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('MarketChannelCategory', MarketChannelCategory);

    MarketChannelCategory.$inject = ['$resource'];

    function MarketChannelCategory ($resource) {
        var resourceUrl =  'api/market-channel-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
