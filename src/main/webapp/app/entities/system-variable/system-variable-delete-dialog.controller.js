(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('SystemVariableDeleteController',SystemVariableDeleteController);

    SystemVariableDeleteController.$inject = ['$uibModalInstance', 'entity', 'SystemVariable'];

    function SystemVariableDeleteController($uibModalInstance, entity, SystemVariable) {
        var vm = this;

        vm.systemVariable = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SystemVariable.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
