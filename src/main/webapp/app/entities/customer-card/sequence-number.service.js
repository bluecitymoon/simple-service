(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('SequenceNumber', SequenceNumber);

    SequenceNumber.$inject = ['$resource'];

    function SequenceNumber ($resource) {
        var resourceUrl =  'api/sequence';

        return $resource(resourceUrl, {}, {
            'getNextSequenceNumber': {
                method: 'GET',
                url: 'api/sequence/get'
            }
        });
    }
})();
