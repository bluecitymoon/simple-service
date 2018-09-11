(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerScheduleDialogController', CustomerScheduleDialogController);

    CustomerScheduleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Customer', 'FreeClassRecord', 'CustomerCommunicationSchedule', 'AlertService'];

    function CustomerScheduleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Customer, FreeClassRecord, CustomerCommunicationSchedule, AlertService) {
        var vm = this;

        vm.customer = entity;
        vm.customerCommunicationSchedule = {};
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        vm.neworders = FreeClassRecord.query({filter: 'customer-is-null'});
        $q.all([vm.customer.$promise, vm.neworders.$promise]).then(function() {
            if (!vm.customer.newOrder || !vm.customer.newOrder.id) {
                return $q.reject();
            }
            return FreeClassRecord.get({id : vm.customer.newOrder.id}).$promise;
        }).then(function(newOrder) {
            vm.neworders.push(newOrder);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            vm.customerCommunicationSchedule.customer = vm.customer;
            vm.customerCommunicationSchedule.follower = vm.customer.newOrder.salesFollower;

            CustomerCommunicationSchedule.save(vm.customerCommunicationSchedule, onSaveSuccess, onSaveError);

        }

        function onSaveSuccess (result) {

            // AlertService.success('Ô¤Ô¼³É¹¦£¡');

            $uibModalInstance.close(result);

            $scope.$emit('simpleServiceApp:customerCommunicationNewLogGenerated', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.sceduleDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
