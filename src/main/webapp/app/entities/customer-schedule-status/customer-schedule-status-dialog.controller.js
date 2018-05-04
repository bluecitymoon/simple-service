(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerScheduleStatusDialogController', CustomerScheduleStatusDialogController);

    CustomerScheduleStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerScheduleStatus'];

    function CustomerScheduleStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerScheduleStatus) {
        var vm = this;

        vm.customerScheduleStatus = entity;
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
            if (vm.customerScheduleStatus.id !== null) {
                CustomerScheduleStatus.update(vm.customerScheduleStatus, onSaveSuccess, onSaveError);
            } else {
                CustomerScheduleStatus.save(vm.customerScheduleStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerScheduleStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
