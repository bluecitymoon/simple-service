(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationLogTypeDetailController', CustomerCommunicationLogTypeDetailController);

    CustomerCommunicationLogTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCommunicationLogType'];

    function CustomerCommunicationLogTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCommunicationLogType) {
        var vm = this;

        vm.customerCommunicationLogType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCommunicationLogTypeUpdate', function(event, result) {
            vm.customerCommunicationLogType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
