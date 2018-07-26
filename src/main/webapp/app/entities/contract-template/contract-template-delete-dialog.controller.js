(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractTemplateDeleteController',ContractTemplateDeleteController);

    ContractTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContractTemplate'];

    function ContractTemplateDeleteController($uibModalInstance, entity, ContractTemplate) {
        var vm = this;

        vm.contractTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContractTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
