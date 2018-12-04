(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCousultantAssignHistoryDialogController', CustomerCousultantAssignHistoryDialogController);

    CustomerCousultantAssignHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCousultantAssignHistory', 'Customer'];

    function CustomerCousultantAssignHistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCousultantAssignHistory, Customer) {
        var vm = this;

        vm.customerCousultantAssignHistory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customers = Customer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerCousultantAssignHistory.id !== null) {
                CustomerCousultantAssignHistory.update(vm.customerCousultantAssignHistory, onSaveSuccess, onSaveError);
            } else {
                CustomerCousultantAssignHistory.save(vm.customerCousultantAssignHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCousultantAssignHistoryUpdate', result);
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
