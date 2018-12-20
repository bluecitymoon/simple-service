(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogDailyReportController', StudentClassLogDailyReportController);

    StudentClassLogDailyReportController.$inject = ['$state', 'StudentClassLogDailyReport', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'DateUtils'];

    function StudentClassLogDailyReportController($state, StudentClassLogDailyReport, ParseLinks, AlertService, paginationConstants, pagingParams, DateUtils) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.statusBasedStudent = {};
        vm.logDateCondition = {
            logDate: new Date()
        };
        vm.students = [];
        vm.years = [];
        vm.months = [];
        vm.datePickerOpenStatus = {};
        vm.searchCondition = {
            yearObject: {id: new Date().getFullYear(), label: new Date().getFullYear()},
            monthObject: {id: new Date().getMonth(), label: new Date().getMonth()}
        };
        vm.openCalendar =  function (date) {
            vm.datePickerOpenStatus[date] = true;
        };

        vm.datePickerOptions = {             showMeridian: false         };
        vm.loadAll = loadAll;

        initData();
        // loadAll();

        function initData() {

            for (var i = 2018; i < 3018; i ++) {
                vm.years.push({id : i, label: i});
            }

            for (var j = 1; j < 13; j ++) {
                vm.months.push({id : j, label: j});
            }

            var year = new Date().getFullYear();
            var month = new Date().getMonth() + 1;

            var thisYear = vm.years.filter(function (y) {
                return y.id == year
            })[0];

            var thisMonth = vm.months.filter(function (m) {
                return m.id == month
            })[0];

            vm.searchCondition.yearObject = thisYear;
            vm.searchCondition.monthObject = thisMonth;

        }
        vm.studentClassLogDailyReport = {
            shouldTaken: null,
            leave: null,
            absence: null,
            added: null,
            actualTaken: null,
            logDate: null,
            id: null
        };

        // vm.statusBasedStudent.logDate = new Date();

        vm.searchConfirmationDetail = function () {

            StudentClassLogDailyReport.getStudentClassLogDailyReportToday({logDate: vm.logDateCondition.logDate}, function (response) {

                vm.statusBasedStudent = response;

                vm.studentClassLogDailyReport.shouldTaken = vm.statusBasedStudent.shouldTakenStudents.length;
                vm.studentClassLogDailyReport.leave = vm.statusBasedStudent.askedLeaveStudents.length;
                vm.studentClassLogDailyReport.absence = vm.statusBasedStudent.absentStudents.length;
                vm.studentClassLogDailyReport.added = vm.statusBasedStudent.addedStudents.length;
                vm.studentClassLogDailyReport.actualTaken = vm.statusBasedStudent.actualTakenStudents.length;
                vm.studentClassLogDailyReport.logDate = vm.logDateCondition.logDate;

            });

        };
        vm.searchConfirmationDetail();
        // function getStudentClassLogDailyReportToday() {
        //
        //
        // }

        // getStudentClassLogDailyReportToday();

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
            StudentClassLogDailyReport.saveLogDailyReport(vm.studentClassLogDailyReport, onSaveSuccess, onSaveError);
        };

        function loadAll (type) {

            vm.searchCondition.queryType = type;

            if (vm.searchCondition.yearObject) {
                vm.searchCondition.year = vm.searchCondition.yearObject.id;
            }

            if (vm.searchCondition.monthObject) {
                vm.searchCondition.month = vm.searchCondition.monthObject.id;
            }

            StudentClassLogDailyReport.getMonthlyStudentClassLogDailyReport(vm.searchCondition, function (reports) {
                vm.reports = reports;
                // console.log(reports);
            }, onError);


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
           // loadAll();
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
