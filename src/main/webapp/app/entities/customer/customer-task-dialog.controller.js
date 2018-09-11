(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerTaskDialogController', CustomerTaskDialogController);

    CustomerTaskDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Task', 'User', 'TaskStatus', 'CustomerTrackTask'];

    function CustomerTaskDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Task, User, TaskStatus, CustomerTrackTask) {
        var vm = this;

        vm.customer = entity;
        vm.customerTrackTask = {};
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        // vm.users = User.query();
        // vm.taskstatuses = TaskStatus.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerTrackTask.id) {
                CustomerTrackTask.update(vm.customerTrackTask, onSaveSuccess, onSaveError);
            } else {

                vm.customerTrackTask.customer = entity;
                CustomerTrackTask.save(vm.customerTrackTask, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:taskUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.estimateExecuteDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
