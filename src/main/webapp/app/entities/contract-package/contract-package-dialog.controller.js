(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractPackageDialogController', ContractPackageDialogController);

    ContractPackageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContractPackage', 'ContractTemplate', 'CustomerCardType'];

    function ContractPackageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContractPackage, ContractTemplate, CustomerCardType) {
        var vm = this;

        vm.contractPackage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.contracttemplates = ContractTemplate.query();
        vm.customercardtypes = CustomerCardType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contractPackage.id !== null) {
                ContractPackage.update(vm.contractPackage, onSaveSuccess, onSaveError);
            } else {
                ContractPackage.save(vm.contractPackage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:contractPackageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
