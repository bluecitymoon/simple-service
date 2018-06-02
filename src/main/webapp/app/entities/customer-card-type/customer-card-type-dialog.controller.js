(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardTypeDialogController', CustomerCardTypeDialogController);

    CustomerCardTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCardType'];

    function CustomerCardTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCardType) {
        var vm = this;

        vm.customerCardType = entity;
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
            if (vm.customerCardType.id !== null) {
                CustomerCardType.update(vm.customerCardType, onSaveSuccess, onSaveError);
            } else {
                CustomerCardType.save(vm.customerCardType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCardTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
