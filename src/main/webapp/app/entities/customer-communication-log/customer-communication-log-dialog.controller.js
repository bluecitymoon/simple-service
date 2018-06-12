(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationLogDialogController', CustomerCommunicationLogDialogController);

    CustomerCommunicationLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCommunicationLog', 'CustomerCommunicationLogType', 'Customer', 'FreeClassRecord'];

    function CustomerCommunicationLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCommunicationLog, CustomerCommunicationLogType, Customer, FreeClassRecord) {
        var vm = this;

        vm.customerCommunicationLog = entity;

        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customercommunicationlogtypes = CustomerCommunicationLogType.query({ page: 0,  size: 1000 });
        vm.customers = Customer.query({ page: 0,  size: 1000 });
        vm.freeclassrecords = FreeClassRecord.query({ page: 0,  size: 1000 });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        loadSingleCustomer(entity.cid);
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function loadSingleCustomer(cid) {

            if (cid) {

                Customer.get({id : cid}, function (data) {
                    vm.customerCommunicationLog.customer = data;
                }, function (error) {
                    console.debug(error);
                })
            }
        }
        function save () {
            vm.isSaving = true;
            if (vm.customerCommunicationLog.id !== null) {
                CustomerCommunicationLog.update(vm.customerCommunicationLog, onSaveSuccess, onSaveError);
            } else {
                CustomerCommunicationLog.save(vm.customerCommunicationLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCommunicationLogUpdate', result);
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
