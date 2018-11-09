(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassCategoryDialogController', ClassCategoryDialogController);

    ClassCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassCategory', 'FreeClassRecord', 'ClassCategoryBase'];

    function ClassCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassCategory, FreeClassRecord, ClassCategoryBase) {
        var vm = this;

        vm.classCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freeclassrecords = FreeClassRecord.query({ page: 0,  size: 1000 });
        vm.classcategorybases = ClassCategoryBase.query({ page: 0,  size: 1000 });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classCategory.id !== null) {
                ClassCategory.update(vm.classCategory, onSaveSuccess, onSaveError);
            } else {
                ClassCategory.save(vm.classCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
