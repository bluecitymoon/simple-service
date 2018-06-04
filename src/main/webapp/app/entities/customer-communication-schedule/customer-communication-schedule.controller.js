(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationScheduleController', CustomerCommunicationScheduleController);

    CustomerCommunicationScheduleController.$inject = ['$state', 'CustomerCommunicationSchedule', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'CustomerScheduleStatus', 'MarketChannelCategory', 'User'];

    function CustomerCommunicationScheduleController($state, CustomerCommunicationSchedule, ParseLinks, AlertService, paginationConstants, pagingParams, CustomerScheduleStatus, MarketChannelCategory, User) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.customerschedulestatuses = CustomerScheduleStatus.query();
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.searchCondition = {};
        vm.channels = MarketChannelCategory.query();
        vm.pwis = User.query();

        vm.clearConditions = function () {
            vm.searchCondition = {};
        };

        vm.loadAll = function () {

            var param = {
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };

            console.log(vm.searchCondition);

            if (vm.searchCondition.statusId) {
                param["scheduleStatusId.equals"] = vm.searchCondition.statusId;
            }
            if (vm.searchCondition.startDate) {
                param["sceduleDate.greaterOrEqualThan"] = vm.searchCondition.startDate;

            }
            if (vm.searchCondition.endDate) {
                param["sceduleDate.lessOrEqualThan"] = vm.searchCondition.endDate;

            }
            if (vm.searchCondition.customerName) {
                param["customerName.equals"] = vm.searchCondition.customerName;

            }
            if (vm.searchCondition.contactPhoneNumber) {
                param["contactPhoneNumber.equals"] = vm.searchCondition.contactPhoneNumber;
            }
            if (vm.searchCondition.actualStartDate) {
                param["actuallMeetDate.greaterOrEqualThan"] = vm.searchCondition.actualStartDate;
            }
            if (vm.searchCondition.actualEndDate) {
                param["actuallMeetDate.lessOrEqualThan"] = vm.searchCondition.actualEndDate;
            }

            if (vm.searchCondition.channel) {
                param["channelId"] = vm.searchCondition.channel.id;
            }

            if (vm.searchCondition.pwi) {
                param["pwiId"] = vm.searchCondition.pwi.id;
            }
            CustomerCommunicationSchedule.query(param, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.customerCommunicationSchedules = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
        };

        // vm.loadAll();

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

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
