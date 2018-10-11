(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractPackageTypeDialogController', ContractPackageTypeDialogController);

    ContractPackageTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContractPackageType'];

    function ContractPackageTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContractPackageType) {
        var vm = this;

        vm.contractPackageType = entity;
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
            if (vm.contractPackageType.id !== null) {
                ContractPackageType.update(vm.contractPackageType, onSaveSuccess, onSaveError);
            } else {
                ContractPackageType.save(vm.contractPackageType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:contractPackageTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
