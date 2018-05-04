(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerStatusDialogController', CustomerStatusDialogController);

    CustomerStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerStatus'];

    function CustomerStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerStatus) {
        var vm = this;

        vm.customerStatus = entity;
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
            if (vm.customerStatus.id !== null) {
                CustomerStatus.update(vm.customerStatus, onSaveSuccess, onSaveError);
            } else {
                CustomerStatus.save(vm.customerStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
