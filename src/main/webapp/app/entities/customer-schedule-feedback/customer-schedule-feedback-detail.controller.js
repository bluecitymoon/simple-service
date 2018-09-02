(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerScheduleFeedbackDetailController', CustomerScheduleFeedbackDetailController);

    CustomerScheduleFeedbackDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerScheduleFeedback', 'Customer', 'CustomerCommunicationSchedule'];

    function CustomerScheduleFeedbackDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerScheduleFeedback, Customer, CustomerCommunicationSchedule) {
        var vm = this;

        vm.customerScheduleFeedback = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerScheduleFeedbackUpdate', function(event, result) {
            vm.customerScheduleFeedback = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
