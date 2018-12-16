(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentLogDialogController', StudentLogDialogController);

    StudentLogDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'Student', 'Customer', 'ClassArrangement', 'StudentClass', 'AlertService', 'StudentFrozen', 'StudentClassLog', 'CustomerConsumerLog', 'StudentLeave', 'StudentAbsenceLog'];

    function StudentLogDialogController ($timeout, $scope, $uibModalInstance, entity, Student, Customer, ClassArrangement, StudentClass, AlertService, StudentFrozen, StudentClassLog, CustomerConsumerLog, StudentLeave, StudentAbsenceLog) {
        var vm = this;

        vm.student = entity;

        vm.clear = clear;
        vm.searchCondition = {};
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOptions = {
            showMeridian: false
        };

        // $scope.$watch('vm.searchCondition.startDate', function (newVal, oldVal) {
        //
        // });
        //
        // $scope.$watch('vm.searchCondition.endDate', function (newVal, oldVal) {
        //
        //
        // });

        vm.rollbackStudentClassLog = function (studentClassLog) {

            StudentClassLog.rollbackStudentClassLog({id: studentClassLog.id}, function () {
                AlertService.success("成功回退上课记录，耗课记录，回退课时数");

                getStudentClassLogByStudentId();
                getStudentConsumeLog();
            })
        };


        function getStudentClassLogByStudentId() {

            StudentClassLog.getStudentClassLogByStudentId({studentId: vm.student.id}, function (students) {
                vm.studentClassLogs = students;
            });
        }
        getStudentClassLogByStudentId();

        function getStudentConsumeLog() {

            CustomerConsumerLog.query({"studentId.equals": vm.student.id}, function (response) {
                vm.customerConsumerLogs = response;
            });

        }
        function getStudentLeaves() {

            StudentLeave.query({"studentId.equals": vm.student.id}, function (response) {
                vm.studentLeaves = response;
            });

        }
        function getStudentAbsenceLog() {

            StudentAbsenceLog.query({"studentId.equals": vm.student.id}, function (response) {
                vm.studentAbsenceLogs = response;
            });

        }

        function getStudentFronzenLog() {

            StudentFrozen.query({"studentId.equals": vm.student.id}, function (response) {
                vm.studentFrozens = response;
            });

        }

        getStudentConsumeLog();
        getStudentLeaves();
        getStudentAbsenceLog();
        getStudentFronzenLog();

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


        vm.datePickerOpenStatus.conditionStartDate = false;
        vm.datePickerOpenStatus.conditionEndDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
