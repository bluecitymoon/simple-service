(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractStatusDialogController', ContractStatusDialogController);

    ContractStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContractStatus'];

    function ContractStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContractStatus) {
        var vm = this;

        vm.contractStatus = entity;
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
            if (vm.contractStatus.id !== null) {
                ContractStatus.update(vm.contractStatus, onSaveSuccess, onSaveError);
            } else {
                ContractStatus.save(vm.contractStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:contractStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
