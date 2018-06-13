(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('PaymentTypeDialogController', PaymentTypeDialogController);

    PaymentTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PaymentType'];

    function PaymentTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PaymentType) {
        var vm = this;

        vm.paymentType = entity;
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
            if (vm.paymentType.id !== null) {
                PaymentType.update(vm.paymentType, onSaveSuccess, onSaveError);
            } else {
                PaymentType.save(vm.paymentType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:paymentTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
