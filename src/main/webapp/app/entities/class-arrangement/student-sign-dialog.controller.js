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

        vm.toggleAll = function () {
            angular.forEach(vm.students, function (student) {
                student.selected = vm.allSelected;
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
