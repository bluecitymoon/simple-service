(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogTypeDialogController', StudentClassLogTypeDialogController);

    StudentClassLogTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentClassLogType'];

    function StudentClassLogTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentClassLogType) {
        var vm = this;

        vm.studentClassLogType = entity;
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
            if (vm.studentClassLogType.id !== null) {
                StudentClassLogType.update(vm.studentClassLogType, onSaveSuccess, onSaveError);
            } else {
                StudentClassLogType.save(vm.studentClassLogType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentClassLogTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
