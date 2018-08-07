(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCollectionLogDialogController', CustomerCollectionLogDialogController);

    CustomerCollectionLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCollectionLog', 'Collection', 'Customer'];

    function CustomerCollectionLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCollectionLog, Collection, Customer) {
        var vm = this;

        vm.customerCollectionLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.collections = Collection.query();
        vm.customers = Customer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerCollectionLog.id !== null) {
                CustomerCollectionLog.update(vm.customerCollectionLog, onSaveSuccess, onSaveError);
            } else {
                CustomerCollectionLog.save(vm.customerCollectionLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCollectionLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
