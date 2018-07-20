(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerStatusReportDtlDeleteController',CustomerStatusReportDtlDeleteController);

    CustomerStatusReportDtlDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerStatusReportDtl'];

    function CustomerStatusReportDtlDeleteController($uibModalInstance, entity, CustomerStatusReportDtl) {
        var vm = this;

        vm.customerStatusReportDtl = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerStatusReportDtl.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
