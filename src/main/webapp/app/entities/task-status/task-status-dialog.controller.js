(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TaskStatusDialogController', TaskStatusDialogController);

    TaskStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TaskStatus'];

    function TaskStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TaskStatus) {
        var vm = this;

        vm.taskStatus = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.taskStatus.id !== null) {
                TaskStatus.update(vm.taskStatus, onSaveSuccess, onSaveError);
            } else {
                TaskStatus.save(vm.taskStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:taskStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
