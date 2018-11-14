(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentSignDialogController', StudentSignDialogController);

    StudentSignDialogController.$inject = ['entity', 'AlertService', 'DateUtils', 'ClassArrangement', 'StudentClass', '$uibModalInstance', 'StudentClassLog'];

    function StudentSignDialogController(entity, AlertService, DateUtils, ClassArrangement, StudentClass, $uibModalInstance, StudentClassLog) {
        var vm = this;

        vm.classSchedule = entity;
        vm.allSelected = true;
        vm.students = [];
        vm.clear = clear;
        vm.openCalendar = openCalendar;

        vm.toggleAll = function () {
            angular.forEach(vm.students, function (student) {
                student.selected = vm.allSelected;
            });
        };
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
        // loadStudentsInClass();
        loadAllClassArrangements();

        loadStudentsInClass();

        vm.signClassInBatch = function (type) {

            var selectedRecords = vm.students.filter(function (r) {
                return r.selected;
            });

            if (!selectedRecords || selectedRecords.length == 0) {
                AlertService.error("未选择学员！");
                return;
            }

            var request;
            if (type == "single") {

                request = {
                    classId: vm.classSchedule.classId,
                    arrangementIds: [{arrangementId: vm.classSchedule.arrangementId}],
                    students: selectedRecords
                };
            } else {

                var arrangementIds = [];
                angular.forEach(vm.classArrangements, function (arrangement) {

                    if (arrangement.selected) {
                        arrangementIds.push({arrangementId: arrangement.id})
                    }
                });

                if (arrangementIds.length == 0) {
                    AlertService.error("未选择补签日期！");
                    return;
                }
                request = {
                    classId: vm.classSchedule.classId,
                    arrangementIds: arrangementIds,
                    students: selectedRecords
                };
            }

            StudentClassLog.batchSignin(request, function (data) {

                AlertService.success("签到成功！");
                clear();
                console.log(data);
            }, function (error) {
                if (error.data && error.data.detail) {

                    AlertService.error(error.data.detail);
                }
            })
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
