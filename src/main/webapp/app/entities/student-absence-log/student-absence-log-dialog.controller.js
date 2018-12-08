(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentAbsenceLogDialogController', StudentAbsenceLogDialogController);

    StudentAbsenceLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentAbsenceLog', 'Student', 'ClassArrangement'];

    function StudentAbsenceLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentAbsenceLog, Student, ClassArrangement) {
        var vm = this;

        vm.studentAbsenceLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.students = Student.query();
        vm.classarrangements = ClassArrangement.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.studentAbsenceLog.id !== null) {
                StudentAbsenceLog.update(vm.studentAbsenceLog, onSaveSuccess, onSaveError);
            } else {
                StudentAbsenceLog.save(vm.studentAbsenceLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentAbsenceLogUpdate', result);
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
