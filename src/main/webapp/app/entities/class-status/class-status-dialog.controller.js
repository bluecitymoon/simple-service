(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassStatusDialogController', ClassStatusDialogController);

    ClassStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassStatus'];

    function ClassStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassStatus) {
        var vm = this;

        vm.classStatus = entity;
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
            if (vm.classStatus.id !== null) {
                ClassStatus.update(vm.classStatus, onSaveSuccess, onSaveError);
            } else {
                ClassStatus.save(vm.classStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
