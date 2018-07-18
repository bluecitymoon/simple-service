(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationScheduleDialogController', CustomerCommunicationScheduleDialogController);

    CustomerCommunicationScheduleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCommunicationSchedule', 'Customer', 'User', 'CustomerScheduleStatus'];

    function CustomerCommunicationScheduleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCommunicationSchedule, Customer, User, CustomerScheduleStatus) {
        var vm = this;

        vm.customerCommunicationSchedule = entity;

        // if (angular.isEmpty(entity)) {
        //
        // }
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        // vm.customers = Customer.query({ page: 0,  size: 1000 });
        vm.users = User.query({ page: 0,  size: 1000 });
        vm.customerschedulestatuses = CustomerScheduleStatus.query({ page: 0,  size: 1000 });
        // var parameters = {
        //     page: 0,
        //     size: 100000,
        //     sort: 'id,desc',
        //     department: 'recipient'
        // };
        //

        vm.customers = [];

        vm.searchPersonWithKeyword = function (keyword) {

            if (!keyword) return;

            Customer.queryByKeyword({keyword: keyword, sort: 'id,desc', department: 'recipient', size: 50}, function (response) {
                vm.customers = response;
            })
        };
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerCommunicationSchedule.id !== null) {
                CustomerCommunicationSchedule.update(vm.customerCommunicationSchedule, onSaveSuccess, onSaveError);
            } else {
                CustomerCommunicationSchedule.save(vm.customerCommunicationSchedule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCommunicationScheduleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.sceduleDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;
        vm.datePickerOpenStatus.actuallMeetDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
