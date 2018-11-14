(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentFrozenDialogController', StudentFrozenDialogController);

    StudentFrozenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentFrozen', 'Student'];

    function StudentFrozenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentFrozen, Student) {
        var vm = this;

        vm.studentFrozen = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.studentFrozen.id !== null) {
                StudentFrozen.update(vm.studentFrozen, onSaveSuccess, onSaveError);
            } else {
                StudentFrozen.save(vm.studentFrozen, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentFrozenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
