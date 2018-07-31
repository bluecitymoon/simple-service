(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractDeleteController',ContractDeleteController);

    ContractDeleteController.$inject = ['$uibModalInstance', 'entity', 'Contract'];

    function ContractDeleteController($uibModalInstance, entity, Contract) {
        var vm = this;

        vm.contract = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Contract.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
