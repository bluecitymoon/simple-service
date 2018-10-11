(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractPackageTypeDeleteController',ContractPackageTypeDeleteController);

    ContractPackageTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContractPackageType'];

    function ContractPackageTypeDeleteController($uibModalInstance, entity, ContractPackageType) {
        var vm = this;

        vm.contractPackageType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContractPackageType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
