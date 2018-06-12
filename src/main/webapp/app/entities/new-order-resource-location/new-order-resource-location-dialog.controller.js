(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderResourceLocationDialogController', NewOrderResourceLocationDialogController);

    NewOrderResourceLocationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NewOrderResourceLocation'];

    function NewOrderResourceLocationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NewOrderResourceLocation) {
        var vm = this;

        vm.newOrderResourceLocation = entity;
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
            if (vm.newOrderResourceLocation.id !== null) {
                NewOrderResourceLocation.update(vm.newOrderResourceLocation, onSaveSuccess, onSaveError);
            } else {
                NewOrderResourceLocation.save(vm.newOrderResourceLocation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:newOrderResourceLocationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
