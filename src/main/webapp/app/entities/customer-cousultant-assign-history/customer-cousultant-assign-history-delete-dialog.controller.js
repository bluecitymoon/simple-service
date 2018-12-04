(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCousultantAssignHistoryDeleteController',CustomerCousultantAssignHistoryDeleteController);

    CustomerCousultantAssignHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCousultantAssignHistory'];

    function CustomerCousultantAssignHistoryDeleteController($uibModalInstance, entity, CustomerCousultantAssignHistory) {
        var vm = this;

        vm.customerCousultantAssignHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCousultantAssignHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
