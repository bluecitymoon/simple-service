(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentMgrDialogController', StudentMgrDialogController);

    StudentMgrDialogController.$inject = ['entity', 'AlertService', 'DateUtils', 'ClassArrangement', 'StudentClass', '$uibModalInstance', 'StudentClassLog', 'Student'];

    function StudentMgrDialogController(entity, AlertService, DateUtils, ClassArrangement, StudentClass, $uibModalInstance, StudentClassLog, Student) {
        var vm = this;

        vm.classSchedule = entity;
        vm.allSelected = true;
        vm.students = [];
        vm.preSelectedStudents = [];

        vm.selectedStudent = {};

        vm.clear = clear;

        vm.toggleAll = function () {
            angular.forEach(vm.students, function (student) {
                student.selected = vm.allSelected;
            });
        };

        vm.searchStudentWithKeyword = function (keyword) {

            console.log(keyword);

            if (!keyword) {
                return;
            }

            Student.searchStudentsWithKeyword({keyword: keyword}, function (data) {
                vm.preSelectedStudents = data;
            })
        };

        function loadStudentsInClass() {

            StudentClass.getAllStudentInClass({classId: vm.classSchedule.classId}, function (data) {
                vm.students = data;

                angular.forEach(vm.students, function (s) {
                    s.selected = true;
                })
            });
        }

        vm.assignStudentIntoClass = function () {

            if (!vm.selectedStudent.id) {
                AlertService.error("未选择学员!");

                return;
            }

            var request = {
                studentId: vm.selectedStudent.id,
                classId: vm.classSchedule.classId
            };

            StudentClass.createSingleStudentClass(request, function (data) {

                AlertService.success("分配成功！");
                vm.students.push(data.student);

            }, function (error) {

                if (error.data && error.data.detail) {

                    AlertService.error(error.data.detail);
                }
            })

        };
        loadStudentsInClass();

        vm.signClassInBatch = function () {

            var selectedRecords = vm.students.filter(function (r) {
                return r.selected;
            });

            if (!selectedRecords || selectedRecords.length == 0) {
                AlertService.error("未选择学员签到");
                return;
            }

            var request = {
                classId: vm.classSchedule.classId,
                arrangementId: vm.classSchedule.arrangementId,
                students: selectedRecords
            };

            StudentClassLog.batchSignin(request, function (data) {

                AlertService.success("签到成功！");
                clear();
                console.log(data);
            })
        };

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
