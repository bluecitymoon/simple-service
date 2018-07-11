(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractNatureDialogController', ContractNatureDialogController);

    ContractNatureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContractNature'];

    function ContractNatureDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContractNature) {
        var vm = this;

        vm.contractNature = entity;
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
            if (vm.contractNature.id !== null) {
                ContractNature.update(vm.contractNature, onSaveSuccess, onSaveError);
            } else {
                ContractNature.save(vm.contractNature, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:contractNatureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
