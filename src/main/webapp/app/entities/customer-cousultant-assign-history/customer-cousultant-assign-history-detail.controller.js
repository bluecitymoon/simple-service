(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCousultantAssignHistoryDetailController', CustomerCousultantAssignHistoryDetailController);

    CustomerCousultantAssignHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCousultantAssignHistory', 'Customer'];

    function CustomerCousultantAssignHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCousultantAssignHistory, Customer) {
        var vm = this;

        vm.customerCousultantAssignHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCousultantAssignHistoryUpdate', function(event, result) {
            vm.customerCousultantAssignHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
