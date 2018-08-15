(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementDialogController', ClassArrangementDialogController);

    ClassArrangementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassArrangement', 'Teacher', 'ClassArrangementStatus'];

    function ClassArrangementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassArrangement, Teacher, ClassArrangementStatus) {
        var vm = this;

        vm.classArrangement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.teachers = Teacher.query();
        vm.classarrangementstatuses = ClassArrangementStatus.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classArrangement.id !== null) {
                ClassArrangement.update(vm.classArrangement, onSaveSuccess, onSaveError);
            } else {
                ClassArrangement.save(vm.classArrangement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classArrangementUpdate', result);
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
