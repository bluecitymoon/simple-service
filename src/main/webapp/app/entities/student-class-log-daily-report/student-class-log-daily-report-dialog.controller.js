(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogDailyReportDialogController', StudentClassLogDailyReportDialogController);

    StudentClassLogDailyReportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentClassLogDailyReport'];

    function StudentClassLogDailyReportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentClassLogDailyReport) {
        var vm = this;

        vm.studentClassLogDailyReport = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.studentClassLogDailyReport.id !== null) {
                StudentClassLogDailyReport.update(vm.studentClassLogDailyReport, onSaveSuccess, onSaveError);
            } else {
                StudentClassLogDailyReport.save(vm.studentClassLogDailyReport, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentClassLogDailyReportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.logDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
