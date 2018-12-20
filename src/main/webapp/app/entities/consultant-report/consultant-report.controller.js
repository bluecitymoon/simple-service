(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ConsultantReportController', ConsultantReportController);

    ConsultantReportController.$inject = ['$state', 'ConsultantReport', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Contract', '$uibModal'];

    function ConsultantReportController($state, ConsultantReport, ParseLinks, AlertService, paginationConstants, pagingParams, Contract, $uibModal) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;


        vm.total = {
            visit: 0,
            dealedAmount: 0
        };

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

        function loadAll (type) {

            vm.searchCondition.queryType = type;

            if (vm.searchCondition.yearObject) {
                vm.searchCondition.year = vm.searchCondition.yearObject.id;
            }

            if (vm.searchCondition.monthObject) {
                vm.searchCondition.month = vm.searchCondition.monthObject.id;
            }

            Contract.getCourseConsultantWorkReport(vm.searchCondition, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.total =  {
                    visit: 0,
                    dealedAmount: 0
                };
                vm.consultantReports = data;

                angular.forEach(vm.consultantReports, function (report) {

                    vm.total.visit = vm.total.visit + report.visitedCount;
                    vm.total.dealedAmount = vm.total.dealedAmount + report.dealedMoneyAmount;
                })
            }
            function onError(error) {
                AlertService.showCommonError(error);
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

        vm.viewDetails = function (report) {

            $uibModal.open({
                templateUrl: 'app/entities/consultant-report/contract-dialog.html',
                controller: 'ConsultantReportContractDetailDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return report.contracts;
                    }
                }
            }).result.then(function() {
            }, function() {
            });
        }
    }
})();
