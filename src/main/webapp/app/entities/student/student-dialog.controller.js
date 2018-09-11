(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentDialogController', StudentDialogController);

    StudentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Student', 'Customer'];

    function StudentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Student, Customer) {
        var vm = this;

        vm.student = entity;

        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        vm.customerId = $stateParams.cid;

        vm.searchPersonWithKeyword = function (keyword) {

            if (!keyword) return;

            Customer.queryByKeyword({keyword: keyword, sort: 'id,desc', department: 'operation', size: 50}, function (response) {
                vm.customers = response;
            })
        };

        $scope.$watch("vm.student.customer", function (newVal, oldVal) {

            if (newVal) {
                vm.student.name = vm.student.customer.name;
                vm.student.gender = vm.student.customer.sex;
                vm.student.school = vm.student.customer.school;
                vm.student.address = vm.student.customer.address;
                vm.student.birthday = vm.student.customer.birthday;
                vm.student.phone = vm.student.customer.contactPhoneNumber;
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
            if (vm.student.id !== null) {
                Student.update(vm.student, onSaveSuccess, onSaveError);
            } else {
                Student.save(vm.student, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:studentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthday = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
