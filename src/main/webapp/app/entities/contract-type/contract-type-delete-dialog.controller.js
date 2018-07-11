(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractTypeDeleteController',ContractTypeDeleteController);

    ContractTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContractType'];

    function ContractTypeDeleteController($uibModalInstance, entity, ContractType) {
        var vm = this;

        vm.contractType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContractType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
