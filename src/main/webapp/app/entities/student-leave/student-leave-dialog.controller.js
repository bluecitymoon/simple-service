(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentLeaveDialogController', StudentLeaveDialogController);

    StudentLeaveDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentLeave', 'Student', 'ClassArrangement'];

    function StudentLeaveDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentLeave, Student, ClassArrangement) {
        var vm = this;

        vm.studentLeave = entity;
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
            if (vm.studentLeave.id !== null) {
                StudentLeave.update(vm.studentLeave, onSaveSuccess, onSaveError);
            } else {
                StudentLeave.save(vm.studentLeave, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentLeaveUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.calculateStartDate = false;
        vm.datePickerOpenStatus.calculateEndDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
