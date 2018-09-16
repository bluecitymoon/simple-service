(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('SingleClassArrangmentDialogController', SingleClassArrangmentDialogController);

    SingleClassArrangmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contract', 'Student', 'Course', 'ContractStatus', 'Product', 'CustomerCard', 'Customer', 'AlertService', 'DateUtils', 'ContractPackage'];

    function SingleClassArrangmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Contract, Student, Course, ContractStatus, Product, CustomerCard, Customer, AlertService, DateUtils, ContractPackage) {
        var vm = this;

        vm.contract = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOptions = {
            showMeridian: false
        };
        vm.save = save;

        vm.classroomId = $stateParams.cr_id;
        vm.products = Product.query({"size" : 100, "classRoomId.equals": vm.classroomId});

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

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
