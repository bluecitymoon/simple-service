(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FinanceCategoryDeleteController',FinanceCategoryDeleteController);

    FinanceCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'FinanceCategory'];

    function FinanceCategoryDeleteController($uibModalInstance, entity, FinanceCategory) {
        var vm = this;

        vm.financeCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FinanceCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
