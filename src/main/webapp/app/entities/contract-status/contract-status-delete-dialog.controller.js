(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractStatusDeleteController',ContractStatusDeleteController);

    ContractStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContractStatus'];

    function ContractStatusDeleteController($uibModalInstance, entity, ContractStatus) {
        var vm = this;

        vm.contractStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContractStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
