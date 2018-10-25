(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ChannelReport', ChannelReport);

    ChannelReport.$inject = ['$resource'];

    function ChannelReport ($resource) {
        var resourceUrl =  'api/channel-reports/:id';

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
