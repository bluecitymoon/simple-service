(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardDialogController', CustomerCardDialogController);

    CustomerCardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCard', 'Customer', 'CustomerCardType'];

    function CustomerCardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCard, Customer, CustomerCardType) {
        var vm = this;

        vm.customerCard = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customers = Customer.query();
        vm.customercardtypes = CustomerCardType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerCard.id !== null) {
                CustomerCard.update(vm.customerCard, onSaveSuccess, onSaveError);
            } else {
                CustomerCard.save(vm.customerCard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.signDate = false;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
