(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ChannelReportController', ChannelReportController);

    ChannelReportController.$inject = ['$state', 'ChannelReport', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'Customer'];

    function ChannelReportController($state, ChannelReport, ParseLinks, AlertService, paginationConstants, pagingParams, Customer) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.channelReports = [];
        vm.years = [];
        vm.months = [];
        vm.datePickerOpenStatus = {};
        vm.searchCondition = {
            yearObject: {id: new Date().getFullYear(), label: new Date().getFullYear()},
            monthObject: {id: new Date().getMonth(), label: new Date().getMonth()}
        };
        vm.openCalendar =  function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        };

        vm.datePickerOptions = {
            showMeridian: false
        };

        // loadAll();
        //
        // function loadAll () {
        //     ChannelReport.query({
        //         page: pagingParams.page - 1,
        //         size: vm.itemsPerPage,
        //         sort: sort()
        //     }, onSuccess, onError);
        //     function sort() {
        //         var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
        //         if (vm.predicate !== 'id') {
        //             result.push('id');
        //         }
        //         return result;
        //     }
        //     function onSuccess(data, headers) {
        //         vm.links = ParseLinks.parse(headers('link'));
        //         vm.totalItems = headers('X-Total-Count');
        //         vm.queryCount = vm.totalItems;
        //         vm.channelReports = data;
        //         vm.page = pagingParams.page;
        //     }
        //     function onError(error) {
        //         AlertService.error(error.data.message);
        //     }
        // }

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
        
        vm.loadReport = function(queryType) {

            vm.searchCondition.queryType = queryType;
            if (vm.searchCondition.yearObject) {
                vm.searchCondition.year = vm.searchCondition.yearObject.id;
            }

            if (vm.searchCondition.monthObject) {
                vm.searchCondition.month = vm.searchCondition.monthObject.id;
            }
            Customer.getVistedCustomerStatusReport(vm.searchCondition, function (data) {
                vm.channelReports = data;
            } );
        };

        vm.loadReport('monthly');

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
    }
})();
