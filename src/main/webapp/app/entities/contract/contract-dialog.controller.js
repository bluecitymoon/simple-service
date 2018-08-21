(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractDialogController', ContractDialogController);

    ContractDialogController.$inject = ['$uibModal','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contract', 'Student', 'Course', 'ContractStatus', 'Product', 'CustomerCard', 'Customer', 'AlertService', 'DateUtils'];

    function ContractDialogController ($uibModal, $timeout, $scope, $stateParams, $uibModalInstance, entity, Contract, Student, Course, ContractStatus, Product, CustomerCard, Customer, AlertService, DateUtils) {
        var vm = this;

        vm.contract = entity;
        if (vm.contract.contractType && vm.contract.contractType == 'free') {

            vm.contract.moneyShouldCollected = 0;
            vm.contract.totalMoneyAmount = 0;
        }

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

                loadStudents(newVal.id);
            }
        });

        $scope.$watch("vm.contract.customerCard", function (newVal, oldVal) {

            if (newVal && !vm.contract.id) {
                //angular.copy(newVal, vm.contract);
                console.log(newVal);
                vm.contract.classCount = newVal.classCount;
                vm.contract.course = newVal.course;
                vm.contract.endDate = DateUtils.convertDateTimeFromServer(newVal.endDate);
                // vm.contract.moneyCollected = newVal.moneyCollected;
                // vm.contract.moneyShouldCollected = newVal.moneyShouldCollected;
                vm.contract.promotionAmount = newVal.promotionAmount;
                vm.contract.serialNumber = newVal.serialNumber;
                vm.contract.signDate = DateUtils.convertDateTimeFromServer(newVal.signDate);
                vm.contract.specialPromotionAmount = newVal.specialPromotionAmount;
                vm.contract.startDate = DateUtils.convertDateTimeFromServer(newVal.startDate);
                vm.contract.totalHours = newVal.totalMinutes;
                // vm.contract.totalMoneyAmount = newVal.totalMoneyAmount;
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

                if (vm.contract.contractType && vm.contract.contractType == 'free') {

                    Contract.createFreeContract(vm.contract, onSaveSuccess, onSaveError);
                } else {
                    Contract.save(vm.contract, onSaveSuccess, onSaveError);

                }
            }
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

        vm.openAddStudentDialog = function () {

            if (!vm.contract.customer) {
                AlertService.warning("未知的客户信息，无法为该客户添加学员");

                return;
            }

            $uibModal.open({
                templateUrl: 'app/entities/student/student-dialog.html',
                controller: 'StudentDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            phone: null,
                            gender: null,
                            birthday: null,
                            address: null,
                            school: null,
                            qq: null,
                            comments: null,
                            id: null,
                            customer: vm.contract.customer
                        };
                    }
                }
            }).result.then(function() {
                loadStudents(vm.contract.customer.id);

            }, function() {
            });
        };

        generateContractNumber();

        function generateContractNumber() {

            CustomerCard.getSequenceNumber({}, function (data) {
                vm.contract.contractNumber  = data.number;
            }, function (error) {
                AlertService.error('生成流水号失败 ' + error);
            });
        }

        //loadStudents();

        function loadStudents(customerId) {

            Student.query({
                page: 0,
                size: 100,
                sort: 'id',
                'customerId.equals': customerId
            }, function (data) {
                vm.students = data;

                if (!data || data.length == 0) {
                    AlertService.info("该客户无相关学员信息，请点击加号增加学员。");
                }
            }, function (error) {
                //
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
