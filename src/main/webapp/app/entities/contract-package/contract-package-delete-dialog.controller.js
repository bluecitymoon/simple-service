(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractPackageDeleteController',ContractPackageDeleteController);

    ContractPackageDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContractPackage'];

    function ContractPackageDeleteController($uibModalInstance, entity, ContractPackage) {
        var vm = this;

        vm.contractPackage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContractPackage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
