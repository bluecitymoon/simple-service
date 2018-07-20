(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerStatusReportDtlDialogController', CustomerStatusReportDtlDialogController);

    CustomerStatusReportDtlDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerStatusReportDtl'];

    function CustomerStatusReportDtlDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerStatusReportDtl) {
        var vm = this;

        vm.customerStatusReportDtl = entity;
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
            if (vm.customerStatusReportDtl.id !== null) {
                CustomerStatusReportDtl.update(vm.customerStatusReportDtl, onSaveSuccess, onSaveError);
            } else {
                CustomerStatusReportDtl.save(vm.customerStatusReportDtl, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerStatusReportDtlUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
