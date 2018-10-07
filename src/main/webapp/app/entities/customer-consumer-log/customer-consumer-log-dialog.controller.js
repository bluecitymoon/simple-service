(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerConsumerLogDialogController', CustomerConsumerLogDialogController);

    CustomerConsumerLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerConsumerLog', 'CustomerConsumerType', 'Student'];

    function CustomerConsumerLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerConsumerLog, CustomerConsumerType, Student) {
        var vm = this;

        vm.customerConsumerLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customerconsumertypes = CustomerConsumerType.query();
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerConsumerLog.id !== null) {
                CustomerConsumerLog.update(vm.customerConsumerLog, onSaveSuccess, onSaveError);
            } else {
                CustomerConsumerLog.save(vm.customerConsumerLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerConsumerLogUpdate', result);
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
