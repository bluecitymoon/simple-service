(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassInOutLogDialogController', StudentClassInOutLogDialogController);

    StudentClassInOutLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentClassInOutLog', 'Student', 'Product'];

    function StudentClassInOutLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentClassInOutLog, Student, Product) {
        var vm = this;

        vm.studentClassInOutLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.studentClassInOutLog.id !== null) {
                StudentClassInOutLog.update(vm.studentClassInOutLog, onSaveSuccess, onSaveError);
            } else {
                StudentClassInOutLog.save(vm.studentClassInOutLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentClassInOutLogUpdate', result);
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
