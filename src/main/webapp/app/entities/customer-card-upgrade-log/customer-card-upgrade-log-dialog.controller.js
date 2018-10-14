(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardUpgradeLogDialogController', CustomerCardUpgradeLogDialogController);

    CustomerCardUpgradeLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCardUpgradeLog', 'CustomerCardType'];

    function CustomerCardUpgradeLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCardUpgradeLog, CustomerCardType) {
        var vm = this;

        vm.customerCardUpgradeLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customercardtypes = CustomerCardType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerCardUpgradeLog.id !== null) {
                CustomerCardUpgradeLog.update(vm.customerCardUpgradeLog, onSaveSuccess, onSaveError);
            } else {
                CustomerCardUpgradeLog.save(vm.customerCardUpgradeLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCardUpgradeLogUpdate', result);
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
