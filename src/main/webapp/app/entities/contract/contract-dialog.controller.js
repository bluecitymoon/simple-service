(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractDialogController', ContractDialogController);

    ContractDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contract', 'Student', 'Course', 'ContractStatus', 'Product', 'CustomerCard', 'Customer', 'AlertService'];

    function ContractDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Contract, Student, Course, ContractStatus, Product, CustomerCard, Customer, AlertService) {
        var vm = this;

        vm.contract = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.students = Student.query();
        vm.courses = Course.query();
        vm.contractstatuses = ContractStatus.query();
        vm.products = Product.query();
        vm.customercards = [];

        vm.customers = [];
        vm.searchPersonWithKeyword = function (keyword) {

            if (!keyword) return;

            Customer.queryByKeyword({keyword: keyword, sort: 'id,desc', department: 'operation', size: 50}, function (response) {
                vm.customers = response;
            })
        };

        $scope.$watch("vm.contract.customer", function (newVal, oldVal) {

            if (newVal) {
                CustomerCard.getCardsByCustomerId({id: newVal.id}, function (cards) {

                    if (!cards || cards.length == 0) {
                        AlertService.error("该客户没有成卡，无法签约合同！")
                    }

                    vm.customercards = cards;

                    if (vm.customercards.length == 1) {
                        vm.contract.customerCard = cards[0];
                    }
                });
            }
        });

        $scope.$watch("vm.contract.customerCard", function (newVal, oldVal) {

            if (newVal) {
                //angular.copy(newVal, vm.contract);
                console.log(newVal);
            }
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contract.id !== null) {
                Contract.update(vm.contract, onSaveSuccess, onSaveError);
            } else {
                Contract.save(vm.contract, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:contractUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
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
