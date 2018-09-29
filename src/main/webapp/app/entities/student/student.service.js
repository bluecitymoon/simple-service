(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('Student', Student);

    Student.$inject = ['$resource', 'DateUtils'];

    function Student ($resource, DateUtils) {
        var resourceUrl =  'api/students/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthday = DateUtils.convertDateTimeFromServer(data.birthday);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'searchStudentsWithKeyword': { url: 'api/students/search/:keyword', method: 'GET', isArray: true}

        });
    }
})();
