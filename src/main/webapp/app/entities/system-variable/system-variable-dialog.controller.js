(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('SystemVariableDialogController', SystemVariableDialogController);

    SystemVariableDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SystemVariable'];

    function SystemVariableDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SystemVariable) {
        var vm = this;

        vm.systemVariable = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.systemVariable.id !== null) {
                SystemVariable.update(vm.systemVariable, onSaveSuccess, onSaveError);
            } else {
                SystemVariable.save(vm.systemVariable, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:systemVariableUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
