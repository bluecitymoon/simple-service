(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationLogTypeDialogController', CustomerCommunicationLogTypeDialogController);

    CustomerCommunicationLogTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCommunicationLogType'];

    function CustomerCommunicationLogTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCommunicationLogType) {
        var vm = this;

        vm.customerCommunicationLogType = entity;
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
            if (vm.customerCommunicationLogType.id !== null) {
                CustomerCommunicationLogType.update(vm.customerCommunicationLogType, onSaveSuccess, onSaveError);
            } else {
                CustomerCommunicationLogType.save(vm.customerCommunicationLogType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCommunicationLogTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
