(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractDialogPackageController', ContractDialogPackageController);

    ContractDialogPackageController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contract', 'Student', 'Course', 'ContractStatus', 'Product', 'CustomerCard', 'Customer', 'AlertService', 'DateUtils', 'ContractPackage'];

    function ContractDialogPackageController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Contract, Student, Course, ContractStatus, Product, CustomerCard, Customer, AlertService, DateUtils, ContractPackage) {
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

                console.log(newVal);
                var customerCardTypeId = vm.contract.customerCard.customerCardType.id;

                loadAllContractPackages(customerCardTypeId);
            }
        });

        function loadAllContractPackages (customerCardTypeId) {
            ContractPackage.query({
                page: 0,
                size: 100,
                'customerCardTypeId.equals': customerCardTypeId
            }, onSuccess, onError);

            function onSuccess(data, headers) {
                // vm.links = ParseLinks.parse(headers('link'));
                // vm.totalItems = headers('X-Total-Count');
                // vm.queryCount = vm.totalItems;
                vm.contractPackages = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {

            var packageRequest = {
                customerId: vm.contract.customer.id,
                customerCardId: vm.contract.customerCard.id,
                packageId: vm.contract.package.id
            };
            Contract.createPackageContract(packageRequest, onSaveSuccess, onSaveError)
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:contractUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError (error) {
            vm.isSaving = false;
            if (error.data && error.data.detail) {

                AlertService.error(error.data.detail);
            }
        }

        generateContractNumber();

        function generateContractNumber() {

            CustomerCard.getSequenceNumber({}, function (data) {
                vm.contract.contractNumber  = data.number;
            }, function (error) {
                AlertService.error('生成流水号失败 ' + error);
            });
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
