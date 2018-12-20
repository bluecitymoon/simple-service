(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentSignLeaveController', StudentSignLeaveController);

    StudentSignLeaveController.$inject = ['entity', 'AlertService', 'DateUtils', 'ClassArrangement', 'StudentClass', '$uibModalInstance', 'StudentClassLog', 'StudentLeave'];

    function StudentSignLeaveController(entity, AlertService, DateUtils, ClassArrangement, StudentClass, $uibModalInstance, StudentClassLog, StudentLeave) {
        var vm = this;

        vm.classSchedule = entity;
        console.log(vm.classSchedule);
        vm.allSelected = true;
        vm.searchCondition = {};
        vm.datePickerOpenStatus = {};
        vm.students = [];
        vm.classArrangements = [];

        vm.clear = clear;

        vm.toggleAll = function () {
            angular.forEach(vm.students, function (student) {
                student.selected = vm.allSelected;
            });
        };

        vm.openCalendar = openCalendar;

        function loadStudentsInClass() {

            StudentClass.getAllStudentInClass({classId: vm.classSchedule.classId}, function (data) {
                vm.students = data;

                angular.forEach(vm.students, function (s) {
                    s.selected = true;
                })
            });
        }

        function loadAllClassArrangements() {

            ClassArrangement.query({"clazzId.equals": vm.classSchedule.classId, size: 1000, page: 0}, function (arrangements) {
                vm.classArrangements = arrangements;
            });
            
        }
        loadStudentsInClass();
        loadAllClassArrangements();

        vm.loadClassArrangementsInRange = function () {

            ClassArrangement.query(
                {  "clazzId.equals": vm.classSchedule.classId,
                    "startDate.greaterOrEqualThan": vm.searchCondition.createStartDate,
                    "startDate.lessOrEqualThan": vm.searchCondition.createEndDate,
                    size: 1000,
                    page: 0
                },
                function (arrangements) {
                    vm.classArrangements = arrangements;
            });
        };
        vm.createStudentLeaveInBatch = function () {

            var selectedRecords = vm.students.filter(function (r) {
                return r.selected;
            });
            var selectedArrangements = vm.classArrangements.filter(function (r) {
                return r.selected;
            });

            StudentLeave.createBatchStudentLeave({
                arrangements: selectedArrangements,
                students: selectedRecords
            }, function (savedLeaves) {

                if (savedLeaves && savedLeaves.length > 0) {
                    angular.forEach(savedLeaves, function (l) {
                        AlertService.success(l.student.name + "请假成功 \n");
                    })
                } else {

                    AlertService.info("操作完成，选中的学员已经请假成功无须再次请假！");
                }

            });
            //
            // if (!selectedRecords || selectedRecords.length == 0) {
            //     AlertService.error("未选择学员签到");
            //     return;
            // }
            //
            // var request = {
            //     classId: vm.classSchedule.classId,
            //     arrangementId: vm.classSchedule.arrangementId,
            //     students: selectedRecords
            // };
            //
            // StudentClassLog.batchSignin(request, function (data) {
            //
            //     AlertService.success("签到成功！");
            //     clear();
            //     console.log(data);
            // }, function (error) {
            //     if (error.data && error.data.detail) {
            //
            //         AlertService.error(error.data.detail);
            //     }
            // })
        };

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
