(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('VistedCustomerStatusDialogController', VistedCustomerStatusDialogController);

    VistedCustomerStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VistedCustomerStatus'];

    function VistedCustomerStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VistedCustomerStatus) {
        var vm = this;

        vm.vistedCustomerStatus = entity;
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
            if (vm.vistedCustomerStatus.id !== null) {
                VistedCustomerStatus.update(vm.vistedCustomerStatus, onSaveSuccess, onSaveError);
            } else {
                VistedCustomerStatus.save(vm.vistedCustomerStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:vistedCustomerStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
