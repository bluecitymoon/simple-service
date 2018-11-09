(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassCategoryBaseDialogController', ClassCategoryBaseDialogController);

    ClassCategoryBaseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassCategoryBase'];

    function ClassCategoryBaseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassCategoryBase) {
        var vm = this;

        vm.classCategoryBase = entity;
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
            if (vm.classCategoryBase.id !== null) {
                ClassCategoryBase.update(vm.classCategoryBase, onSaveSuccess, onSaveError);
            } else {
                ClassCategoryBase.save(vm.classCategoryBase, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classCategoryBaseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
