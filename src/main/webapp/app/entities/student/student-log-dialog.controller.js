(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentLogDialogController', StudentLogDialogController);

    StudentLogDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'Student', 'Customer', 'ClassArrangement', 'StudentClass', 'AlertService', 'StudentFrozen', 'StudentClassLog'];

    function StudentLogDialogController ($timeout, $scope, $uibModalInstance, entity, Student, Customer, ClassArrangement, StudentClass, AlertService, StudentFrozen, StudentClassLog) {
        var vm = this;

        vm.student = entity;

        vm.clear = clear;
        vm.searchCondition = {};
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOptions = {
            showMeridian: false
        };

        vm.save = save;

        $scope.$watch('vm.searchCondition.startDate', function (newVal, oldVal) {

        });

        $scope.$watch('vm.searchCondition.endDate', function (newVal, oldVal) {


        });


        function getStudentClassLogByStudentId() {

            StudentClassLog.getStudentClassLogByStudentId({studentId: vm.student.id}, function (students) {
                vm.studentClassLogs = students;
            });
        }
        getStudentClassLogByStudentId();

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
