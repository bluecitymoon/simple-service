(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentLockDialogController', StudentLockDialogController);

    StudentLockDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'Student', 'Customer', 'ClassArrangement', 'StudentClass', 'AlertService', 'StudentFrozen'];

    function StudentLockDialogController ($timeout, $scope, $uibModalInstance, entity, Student, Customer, ClassArrangement, StudentClass, AlertService, StudentFrozen) {
        var vm = this;

        vm.student = entity;

        vm.clear = clear;
        vm.searchCondition = {};
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOptions = {
            showMeridian: false
        };
        vm.allSelected = true;

        vm.toggleAll = function () {

            angular.forEach(vm.classArrangements, function (student) {
                student.selected = vm.allSelected;
            });
        };
        vm.save = save;

        $scope.$watch('vm.searchCondition.frozenStartDate', function (newVal, oldVal) {

            if (newVal && vm.searchCondition.frozenEndDate) {
                loadStudentClassArrangements();
            }

        });

        $scope.$watch('vm.searchCondition.frozenEndDate', function (newVal, oldVal) {

            if (newVal && vm.searchCondition.frozenStartDate) {
                loadStudentClassArrangements();
            }

        });

        function loadStudentClassArrangements() {

            ClassArrangement.findStudentArrangements({
                studentId: vm.student.id,
                startDate: vm.searchCondition.frozenStartDate,
                endDate: vm.searchCondition.frozenEndDate
            }, function (response) {
                vm.classArrangements = response;

                angular.forEach(vm.classArrangements, function (arrangement) {
                    arrangement.selected = true;
                });
            }, function (error) {
                AlertService.showCommonError(error);
            })
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            var selectedArrangements = vm.classArrangements.filter(function (arrangement) {
                return arrangement.selected;
            });

            var request = {
                student: vm.student,
                startDate: vm.searchCondition.frozenStartDate,
                endDate: vm.searchCondition.frozenEndDate,
                classArrangements: selectedArrangements
            };

            StudentFrozen.generateStudentFrozen(request, onSaveSuccess, function (error) {
                AlertService.showCommonError(error);
            });
        }

        function onSaveSuccess (result) {
            // $scope.$emit('simpleServiceApp:studentUpdate', result);
            AlertService.success('冻结成功');

            $uibModalInstance.close(result);

            vm.isSaving = false;
        }

        // function onSaveError (error) {
        //     vm.isSaving = false;
        //     if (error.data && error.data.detail) {
        //
        //         AlertService.error(error.data.detail);
        //     }
        // }

        vm.datePickerOpenStatus.conditionStartDate = false;
        vm.datePickerOpenStatus.conditionEndDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
