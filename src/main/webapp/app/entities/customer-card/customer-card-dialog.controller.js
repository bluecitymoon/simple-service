(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardDialogController', CustomerCardDialogController);

    CustomerCardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCard', 'Customer', 'CustomerCardType', 'Course'];

    function CustomerCardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCard, Customer, CustomerCardType, Course) {
        var vm = this;

        vm.customerCard = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customerId = $stateParams.cid;

        if (!vm.customerId) {

            var parameters = {
                page: 0,
                size: 5000,
                sort: 'id,desc',
                department: 'operation'
            };

            vm.customers = Customer.query(parameters);
        }

        vm.customercardtypes = CustomerCardType.query({ page: 0,  size: 1000 });
        vm.courses = Course.query({ page: 0,  size: 1000 });

        function calculateMoney() {
            vm.customerCard.moneyShouldCollected = vm.customerCard.totalMoneyAmount - vm.customerCard.promotionAmount - vm.customerCard.specialPromotionAmount;
        }

        $scope.$watch("vm.customerCard.customerCardType", function (newVal, oldVal) {

            if (newVal) {
                vm.customerCard.totalMoneyAmount = newVal.totalMoneyAmount;
                vm.customerCard.classCount = newVal.classCount;
                vm.customerCard.totalMinutes = newVal.totalMinutes;

               calculateMoney();
            }
        });

        $scope.$watch("vm.customerCard.totalMoneyAmount", function (newVal, oldVal) {

            if (newVal) {
                calculateMoney();
            }
        });
        $scope.$watch("vm.customerCard.promotionAmount", function (newVal, oldVal) {

            if (newVal) {
                calculateMoney();
            }
        });
        $scope.$watch("vm.customerCard.specialPromotionAmount", function (newVal, oldVal) {

            if (newVal) {
                calculateMoney();
            }
        });


        loadSingleCustomer(vm.customerId);
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerCard.id !== null) {
                CustomerCard.update(vm.customerCard, onSaveSuccess, onSaveError);
            } else {
                CustomerCard.save(vm.customerCard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function loadSingleCustomer(cid) {

            if (cid) {

                Customer.get({id : cid}, function (data) {
                    vm.customerCard.customer = data;
                }, function (error) {
                    console.debug(error);
                })
            }
        }
        vm.datePickerOpenStatus.signDate = false;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
