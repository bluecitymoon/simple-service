(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerStatusReportDtlDetailController', CustomerStatusReportDtlDetailController);

    CustomerStatusReportDtlDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerStatusReportDtl'];

    function CustomerStatusReportDtlDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerStatusReportDtl) {
        var vm = this;

        vm.customerStatusReportDtl = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerStatusReportDtlUpdate', function(event, result) {
            vm.customerStatusReportDtl = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
