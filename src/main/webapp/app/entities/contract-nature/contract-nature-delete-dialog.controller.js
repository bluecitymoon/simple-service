(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractNatureDeleteController',ContractNatureDeleteController);

    ContractNatureDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContractNature'];

    function ContractNatureDeleteController($uibModalInstance, entity, ContractNature) {
        var vm = this;

        vm.contractNature = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContractNature.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
