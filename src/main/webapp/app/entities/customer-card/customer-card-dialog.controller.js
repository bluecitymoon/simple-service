(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardDialogController', CustomerCardDialogController);

    CustomerCardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCard', 'Customer', 'CustomerCardType', 'Course', 'SequenceNumber', 'AlertService'];

    function CustomerCardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCard, Customer, CustomerCardType, Course, SequenceNumber, AlertService) {
        var vm = this;

        vm.customerCard = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        vm.customerId = $stateParams.cid;
        vm.customers = [];

        vm.searchPersonWithKeyword = function (keyword) {

            if (!keyword) return;

            Customer.queryByKeyword({keyword: keyword, sort: 'id,desc', department: 'operation', size: 50}, function (response) {
                vm.customers = response;
            })
        };
        // if (!vm.customerId) {
        //
        //     var parameters = {
        //         page: 0,
        //         size: 100000,
        //         sort: 'id,desc',
        //         department: 'operation'
        //     };
        //
        //     vm.customers = Customer.query(parameters);
        // }

        vm.customercardtypes = CustomerCardType.query({ page: 0,  size: 1000 });
        vm.courses = Course.query({ page: 0,  size: 1000 });

        function calculateMoney() {
            vm.customerCard.moneyShouldCollected = vm.customerCard.totalMoneyAmount - vm.customerCard.promotionAmount - vm.customerCard.specialPromotionAmount;
        }

        function generateCustomerCardNumber() {

            CustomerCard.generateCustomerCardNumber({
                customerId: vm.customerCard.customer.id,
                cardCode: vm.customerCard.customerCardType.code
            }, function (data) {

                console.log(data);
                vm.customerCard.number = data.cardNumber;

            }, function (error) {

            });
        }

        $scope.$watch("vm.customerCard.customerCardType", function (newVal, oldVal) {

            if (newVal) {
                vm.customerCard.totalMoneyAmount = newVal.totalMoneyAmount;
                vm.customerCard.classCount = newVal.classCount;
                vm.customerCard.totalMinutes = newVal.totalMinutes;
                vm.customerCard.promotionAmount = newVal.promotionAmount;

               calculateMoney();

                if (vm.customerCard.customer) {
                    generateCustomerCardNumber();
                }
            }
        });

        $scope.$watch("vm.customerCard.customer", function (newVal, oldVal) {

            if (newVal) {

                if (vm.customerCard.customerCardType) {
                    generateCustomerCardNumber();
                }
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

        function generateSequenceNumber() {

            if (vm.customerCard.id == null) {

                 CustomerCard.getSequenceNumber({}, function (data) {
                     vm.customerCard.serialNumber  = data.number;
                }, function (error) {
                    AlertService.error('生成流水号失败 ' + error);
                });

            }
        }

        loadSingleCustomer(vm.customerId);
        generateSequenceNumber();

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
