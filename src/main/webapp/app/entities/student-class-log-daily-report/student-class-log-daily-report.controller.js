(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogDailyReportController', StudentClassLogDailyReportController);

    StudentClassLogDailyReportController.$inject = ['$state', 'StudentClassLogDailyReport', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function StudentClassLogDailyReportController($state, StudentClassLogDailyReport, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.statusBasedStudent = {};

        vm.students = [];
        vm.studentClassLogDailyReport = {
            shouldTaken: null,
            leave: null,
            absence: null,
            added: null,
            actualTaken: null,
            logDate: null,
            id: null
        };

        loadAll();

        function getStudentClassLogDailyReportToday() {

            StudentClassLogDailyReport.getStudentClassLogDailyReportToday({}, function (response) {

                vm.statusBasedStudent = response;

                vm.studentClassLogDailyReport.shouldTaken = vm.statusBasedStudent.shouldTakenStudents.length;
                vm.studentClassLogDailyReport.leave = vm.statusBasedStudent.askedLeaveStudents.length;
                vm.studentClassLogDailyReport.absence = vm.statusBasedStudent.absentStudents.length;
                vm.studentClassLogDailyReport.added = vm.statusBasedStudent.addedStudents.length;
                vm.studentClassLogDailyReport.actualTaken = vm.statusBasedStudent.actualTakenStudents.length;
                vm.studentClassLogDailyReport.logDate = vm.statusBasedStudent.logDate;

            });

        }

        getStudentClassLogDailyReportToday();

        vm.showShouldTakenStudents = function (type) {

            switch (type) {
                case "shouldTaken":

                    vm.students = vm.statusBasedStudent.shouldTakenStudents;
                    break;
                case "leave":

                    vm.students = vm.statusBasedStudent.askedLeaveStudents;
                    break;
                case "absence":

                    vm.students = vm.statusBasedStudent.absentStudents;
                    break;
                case "added":

                    vm.students = vm.statusBasedStudent.addedStudents;
                    break;
                case "actualTaken":

                    vm.students = vm.statusBasedStudent.actualTakenStudents;
                    break;
                default:
                    break;
            }


        };

        vm.confirmDailyReport = function () {
            StudentClassLogDailyReport.save(vm.studentClassLogDailyReport, onSaveSuccess, onSaveError);
        };

        function loadAll () {
            StudentClassLogDailyReport.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.studentClassLogDailyReports = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function onSaveSuccess (result) {
           loadAll();
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
