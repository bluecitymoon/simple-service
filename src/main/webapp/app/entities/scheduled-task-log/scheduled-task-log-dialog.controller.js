(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ScheduledTaskLogDialogController', ScheduledTaskLogDialogController);

    ScheduledTaskLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ScheduledTaskLog'];

    function ScheduledTaskLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ScheduledTaskLog) {
        var vm = this;

        vm.scheduledTaskLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.scheduledTaskLog.id !== null) {
                ScheduledTaskLog.update(vm.scheduledTaskLog, onSaveSuccess, onSaveError);
            } else {
                ScheduledTaskLog.save(vm.scheduledTaskLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:scheduledTaskLogUpdate', result);
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
