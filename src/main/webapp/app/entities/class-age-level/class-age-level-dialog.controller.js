(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassAgeLevelDialogController', ClassAgeLevelDialogController);

    ClassAgeLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassAgeLevel'];

    function ClassAgeLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassAgeLevel) {
        var vm = this;

        vm.classAgeLevel = entity;
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
            if (vm.classAgeLevel.id !== null) {
                ClassAgeLevel.update(vm.classAgeLevel, onSaveSuccess, onSaveError);
            } else {
                ClassAgeLevel.save(vm.classAgeLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classAgeLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
