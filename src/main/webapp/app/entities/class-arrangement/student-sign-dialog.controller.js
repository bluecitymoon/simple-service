(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentSignDialogController', StudentSignDialogController);

    StudentSignDialogController.$inject = ['entity', 'AlertService', 'DateUtils', 'ClassArrangement', 'StudentClass', '$uibModalInstance'];

    function StudentSignDialogController(entity, AlertService, DateUtils, ClassArrangement, StudentClass, $uibModalInstance) {
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

        };

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
