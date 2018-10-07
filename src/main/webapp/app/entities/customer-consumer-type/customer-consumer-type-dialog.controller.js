(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerConsumerTypeDialogController', CustomerConsumerTypeDialogController);

    CustomerConsumerTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerConsumerType'];

    function CustomerConsumerTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerConsumerType) {
        var vm = this;

        vm.customerConsumerType = entity;
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
            if (vm.customerConsumerType.id !== null) {
                CustomerConsumerType.update(vm.customerConsumerType, onSaveSuccess, onSaveError);
            } else {
                CustomerConsumerType.save(vm.customerConsumerType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerConsumerTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
