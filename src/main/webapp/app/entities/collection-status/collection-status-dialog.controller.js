(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CollectionStatusDialogController', CollectionStatusDialogController);

    CollectionStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CollectionStatus'];

    function CollectionStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CollectionStatus) {
        var vm = this;

        vm.collectionStatus = entity;
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
            if (vm.collectionStatus.id !== null) {
                CollectionStatus.update(vm.collectionStatus, onSaveSuccess, onSaveError);
            } else {
                CollectionStatus.save(vm.collectionStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:collectionStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
