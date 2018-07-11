(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractTypeDialogController', ContractTypeDialogController);

    ContractTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContractType'];

    function ContractTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContractType) {
        var vm = this;

        vm.contractType = entity;
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
            if (vm.contractType.id !== null) {
                ContractType.update(vm.contractType, onSaveSuccess, onSaveError);
            } else {
                ContractType.save(vm.contractType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:contractTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
