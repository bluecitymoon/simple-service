(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerDialogController', CustomerDialogController);

    CustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Customer', 'FreeClassRecord', 'CustomerStatus', 'MarketChannelCategory'];

    function CustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Customer, FreeClassRecord, CustomerStatus, MarketChannelCategory) {
        var vm = this;

        vm.customer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
        vm.customerstatuses = CustomerStatus.query();
        vm.marketchannelcategories = MarketChannelCategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customer.id !== null) {
                Customer.update(vm.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save(vm.customer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;
        vm.datePickerOpenStatus.birthday = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
