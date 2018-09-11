(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerMergeDialogController', CustomerMergeDialogController);

    CustomerMergeDialogController.$inject = ['$state','$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Customer', 'FreeClassRecord', 'CustomerStatus', 'MarketChannelCategory', "NewOrderResourceLocation", "VistedCustomerStatus", "AlertService"];

    function CustomerMergeDialogController ($state, $timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Customer, FreeClassRecord, CustomerStatus, MarketChannelCategory, NewOrderResourceLocation, VistedCustomerStatus, AlertService) {
        var vm = this;

        vm.customer = entity;
        vm.targetCustomer = {};
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        vm.vistedStatus = VistedCustomerStatus.query({size: 100});

        vm.locations = NewOrderResourceLocation.query({ page: 0,  size: 1000 });
        vm.neworders = FreeClassRecord.query({filter: 'customer-is-null'});
        $q.all([vm.customer.$promise, vm.neworders.$promise]).then(function() {
            if (!vm.customer.newOrder || !vm.customer.newOrder.id) {
                return $q.reject();
            }
            return FreeClassRecord.get({id : vm.customer.newOrder.id}).$promise;
        }).then(function(newOrder) {
            vm.neworders.push(newOrder);
        });
        vm.customerstatuses = CustomerStatus.query({ page: 0,  size: 1000 });
        vm.marketchannelcategories = MarketChannelCategory.query({ page: 0,  size: 1000 });
        vm.classLevels = [
            {id: 1, value: "成年"},
            {id: 2, value: "学生"},
            {id: 3, value: "幼儿"}
        ];
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        $scope.$watch("vm.targetCustomer", function (newVal, oldVal) {

            if (newVal) {

                if (!vm.targetCustomer.id) {

                    AlertService.info ("请选择目标客户");
                    return;
                }

                Customer.preloadMergedCustomer({oid: vm.customer.id, tid: vm.targetCustomer.id},
                function (customer) {
                    vm.finalCustomer = customer;
                },
                function (error) {
                    console.log(error);
                })
            }
        });

        vm.mergeCustomer = function () {

            var data = {originalId: vm.customer.id, targetId: vm.targetCustomer.id, customer: vm.finalCustomer}
            Customer.mergeCustomer(data, function (response) {
                AlertService.success("合并成功!");

                $uibModalInstance.dismiss('cancel');
                $state.go('customer-detail', {id: response.id});
            })
        };
        vm.searchPersonWithKeyword = function (keyword) {

            if (!keyword) return;

            Customer.queryByKeyword({keyword: keyword, sort: 'id,desc', department: 'market', size: 50}, function (response) {
                vm.customers = response;
            })
        };

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customer.classLevel) {
                vm.customer.classLevel = vm.customer.classLevel.value;
            }
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
