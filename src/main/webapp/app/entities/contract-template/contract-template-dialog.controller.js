(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractTemplateDialogController', ContractTemplateDialogController);

    ContractTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContractTemplate', 'CustomerCardType', 'ContractNature', 'ContractPackage'];

    function ContractTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContractTemplate, CustomerCardType, ContractNature, ContractPackage) {
        var vm = this;

        vm.contractTemplate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        // vm.customercardtypes = CustomerCardType.query();
        vm.contractpackages = ContractPackage.query(); //TODO
        vm.contractnatures = ContractNature.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contractTemplate.id !== null) {
                ContractTemplate.update(vm.contractTemplate, onSaveSuccess, onSaveError);
            } else {
                ContractTemplate.save(vm.contractTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:contractTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
