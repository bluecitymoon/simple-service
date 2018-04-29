(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('NewOrderWechatUserInfo', NewOrderWechatUserInfo);

    NewOrderWechatUserInfo.$inject = ['$resource', 'DateUtils'];

    function NewOrderWechatUserInfo ($resource, DateUtils) {
        var resourceUrl =  'api/new-order-wechat-user-infos/:id';

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
