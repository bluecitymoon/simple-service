(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationScheduleDetailController', CustomerCommunicationScheduleDetailController);

    CustomerCommunicationScheduleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCommunicationSchedule', 'Customer', 'User'];

    function CustomerCommunicationScheduleDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCommunicationSchedule, Customer, User) {
        var vm = this;

        vm.customerCommunicationSchedule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCommunicationScheduleUpdate', function(event, result) {
            vm.customerCommunicationSchedule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
