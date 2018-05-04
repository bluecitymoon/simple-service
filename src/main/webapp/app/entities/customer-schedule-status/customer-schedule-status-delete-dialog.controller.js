(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerScheduleStatusDeleteController',CustomerScheduleStatusDeleteController);

    CustomerScheduleStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerScheduleStatus'];

    function CustomerScheduleStatusDeleteController($uibModalInstance, entity, CustomerScheduleStatus) {
        var vm = this;

        vm.customerScheduleStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerScheduleStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
