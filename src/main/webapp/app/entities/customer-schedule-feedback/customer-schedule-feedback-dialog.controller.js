(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerScheduleFeedbackDialogController', CustomerScheduleFeedbackDialogController);

    CustomerScheduleFeedbackDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerScheduleFeedback', 'Customer', 'CustomerCommunicationSchedule'];

    function CustomerScheduleFeedbackDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerScheduleFeedback, Customer, CustomerCommunicationSchedule) {
        var vm = this;

        vm.customerScheduleFeedback = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customers = Customer.query();
        vm.customercommunicationschedules = CustomerCommunicationSchedule.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerScheduleFeedback.id !== null) {
                CustomerScheduleFeedback.update(vm.customerScheduleFeedback, onSaveSuccess, onSaveError);
            } else {
                CustomerScheduleFeedback.save(vm.customerScheduleFeedback, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerScheduleFeedbackUpdate', result);
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
