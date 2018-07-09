(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FinanceCategoryDialogController', FinanceCategoryDialogController);

    FinanceCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FinanceCategory'];

    function FinanceCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FinanceCategory) {
        var vm = this;

        vm.financeCategory = entity;
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
            if (vm.financeCategory.id !== null) {
                FinanceCategory.update(vm.financeCategory, onSaveSuccess, onSaveError);
            } else {
                FinanceCategory.save(vm.financeCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:financeCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
