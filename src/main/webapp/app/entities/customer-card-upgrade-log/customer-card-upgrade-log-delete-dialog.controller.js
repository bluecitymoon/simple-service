(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardUpgradeLogDeleteController',CustomerCardUpgradeLogDeleteController);

    CustomerCardUpgradeLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCardUpgradeLog'];

    function CustomerCardUpgradeLogDeleteController($uibModalInstance, entity, CustomerCardUpgradeLog) {
        var vm = this;

        vm.customerCardUpgradeLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCardUpgradeLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
