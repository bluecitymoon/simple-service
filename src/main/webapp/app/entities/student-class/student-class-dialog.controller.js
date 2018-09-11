(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassDialogController', StudentClassDialogController);

    StudentClassDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentClass', 'Student', 'Product'];

    function StudentClassDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentClass, Student, Product) {
        var vm = this;

        vm.studentClass = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        vm.students = Student.query();
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.studentClass.id !== null) {
                StudentClass.update(vm.studentClass, onSaveSuccess, onSaveError);
            } else {
                StudentClass.save(vm.studentClass, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentClassUpdate', result);
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
