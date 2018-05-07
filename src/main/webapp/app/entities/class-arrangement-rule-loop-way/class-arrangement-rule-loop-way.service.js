(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('ClassArrangementRuleLoopWay', ClassArrangementRuleLoopWay);

    ClassArrangementRuleLoopWay.$inject = ['$resource'];

    function ClassArrangementRuleLoopWay ($resource) {
        var resourceUrl =  'api/class-arrangement-rule-loop-ways/:id';

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
