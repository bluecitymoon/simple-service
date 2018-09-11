(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogDialogController', StudentClassLogDialogController);

    StudentClassLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentClassLog', 'Student', 'ClassArrangement'];

    function StudentClassLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentClassLog, Student, ClassArrangement) {
        var vm = this;

        vm.studentClassLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
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
            if (vm.studentClassLog.id !== null) {
                StudentClassLog.update(vm.studentClassLog, onSaveSuccess, onSaveError);
            } else {
                StudentClassLog.save(vm.studentClassLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentClassLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.actualTakenDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
