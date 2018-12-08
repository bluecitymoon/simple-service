(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentSignDialogController', StudentSignDialogController);

    StudentSignDialogController.$inject = ['$scope', 'entity', 'AlertService', 'DateUtils', 'ClassArrangement', 'StudentClass', '$uibModalInstance', 'StudentClassLog', 'Teacher', 'Student'];

    function StudentSignDialogController($scope, entity, AlertService, DateUtils, ClassArrangement, StudentClass, $uibModalInstance, StudentClassLog, Teacher, Student) {
        var vm = this;

        vm.classSchedule = entity;
        vm.allSelected = true;
        vm.students = [];
        vm.tempStudents = [];
        vm.allTeachers = Teacher.query({size: 1000, page: 0});
        vm.clear = clear;
        vm.openCalendar = openCalendar;

        vm.singleRowClicked = function (student, type) {

            switch (type) {
                case "absent":

                    if (student.absent) {
                        student.selected = false;
                    }

                    break;
                case "normal":

                    if (student.selected) {
                        student.absent = false;
                    }

                    break;
                default:
                    break;
            }
        };

        vm.toggleAll = function () {
            angular.forEach(vm.students, function (student) {
                student.selected = vm.allSelected;
            });
        };

        vm.searchStudentWithKeyword = function (keyword) {

            if (!keyword) {
                return;
            }

            Student.searchStudentsWithKeyword({keyword: keyword}, function (data) {
                vm.preaddedStudents = data;
            })
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

        loadPlannedTeacher();
        function loadPlannedTeacher() {
            //vm.actualTeacher

            ClassArrangement.get({id: vm.classSchedule.arrangementId}, function (classArrangement) {
                vm.actualTeacher = classArrangement.planedTeacher;
            })
        }

        vm.studentClassLogs = [];
        function loadAllStudentClassLogs() {
            vm.studentClassLogs = StudentClassLog.query({size: 10000, page:0, "classId": vm.classSchedule.classId});
        }

        loadAllStudentClassLogs();

        vm.signClassInBatch = function (type) {

            var selectedRecords = vm.students.filter(function (r) {
                return r.selected;
            });

            var absentStudents = vm.students.filter(function (r) {
                return r.absent;
            });
            //
            // if (!selectedRecords || selectedRecords.length == 0) {
            //     AlertService.error("未选择学员！");
            //     return;
            // }

            var request;
            if (type == "single") {

                request = {
                    classId: vm.classSchedule.classId,
                    arrangementIds: [
                        {
                            arrangementId: vm.classSchedule.arrangementId,
                            actualTeacher: vm.actualTeacher
                        }],
                    students: selectedRecords,
                    absentStudents: absentStudents,
                    addedStudents: vm.addedStudents
                };
            } else {

                var arrangementIds = [];
                angular.forEach(vm.classArrangements, function (arrangement) {

                    if (arrangement.selected) {
                        arrangementIds.push({arrangementId: arrangement.id, actualTeacher: arrangement.planedTeacher})
                    }
                });

                if (arrangementIds.length == 0) {
                    AlertService.error("未选择补签日期！");
                    return;
                }
                request = {
                    classId: vm.classSchedule.classId,
                    arrangementIds: arrangementIds,
                    students: selectedRecords,
                    absentStudents: absentStudents,
                    addedStudents: vm.addedStudents
                };
            }

            StudentClassLog.batchSignin(request, function (data) {

                AlertService.success("签到成功！");
                clear();
                console.log(data);

                // loadAllStudentClassLogs();
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
