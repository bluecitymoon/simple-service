(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationLogDetailController', CustomerCommunicationLogDetailController);

    CustomerCommunicationLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCommunicationLog', 'CustomerCommunicationLogType', 'Customer', 'FreeClassRecord'];

    function CustomerCommunicationLogDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCommunicationLog, CustomerCommunicationLogType, Customer, FreeClassRecord) {
        var vm = this;

        vm.customerCommunicationLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCommunicationLogUpdate', function(event, result) {
            vm.customerCommunicationLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
