(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardUpgradeLogDetailController', CustomerCardUpgradeLogDetailController);

    CustomerCardUpgradeLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCardUpgradeLog', 'CustomerCardType'];

    function CustomerCardUpgradeLogDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCardUpgradeLog, CustomerCardType) {
        var vm = this;

        vm.customerCardUpgradeLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCardUpgradeLogUpdate', function(event, result) {
            vm.customerCardUpgradeLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
