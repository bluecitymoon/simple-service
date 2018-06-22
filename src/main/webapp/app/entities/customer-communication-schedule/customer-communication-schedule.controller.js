(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationScheduleController', CustomerCommunicationScheduleController);

    CustomerCommunicationScheduleController.$inject = ['$scope', '$rootScope','$state', 'CustomerCommunicationSchedule', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'CustomerScheduleStatus', 'MarketChannelCategory', 'User'];

    function CustomerCommunicationScheduleController($scope, $rootScope, $state, CustomerCommunicationSchedule, ParseLinks, AlertService, paginationConstants, pagingParams, CustomerScheduleStatus, MarketChannelCategory, User) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.customerschedulestatuses = CustomerScheduleStatus.query({ page: 0,  size: 1000 });
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.searchCondition = {};
        vm.channels = MarketChannelCategory.query({ page: 0,  size: 1000 });
        vm.pwis = User.getAllPwis();
        vm.sales = User.getAllSales();
        vm.consultants = User.getAllCourseConsultant();

        vm.batchAssignNewOrder = function () {
            var selectedRecords = vm.customerCommunicationSchedules.filter(function (r) {
                return r.selected;
            });

            if (!selectedRecords || selectedRecords.length == 0) {
                AlertService.error("没有选中任何客户记录，无法分配！");
                return;
            }

            if (!vm.selectedUser) {
                AlertService.error("请选择目标用户！");
                return;
            }

            selectedRecords.forEach(function (c) {
                c.customer.courseConsultant = vm.selectedUser;
            });

            CustomerCommunicationSchedule.batchUpdate(selectedRecords, function (response) {

                AlertService.success("操作成功！批量分配了" + response.length + "条客户数据到用户" + vm.selectedUser.firstName+ "！");

            }, function (error) {

                AlertService.error(error);
            });

        };
        vm.toggleAll = function () {
            vm.customerCommunicationSchedules.forEach(function (record) {
                record.selected = vm.allSelected;
            })
        };
        vm.clearConditions = function () {
            vm.searchCondition = {};
        };

        $scope.pagination = {
            currentPageNumber: 1,
            totalItems: 0
        };
        vm.loadAll = function () {

            var param = {
                page: $scope.pagination.currentPageNumber - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };

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
            if (vm.searchCondition.tmk) {
                param["tmkId"] = vm.searchCondition.tmk.id;
            }
            if (vm.searchCondition.courseConsultant) {
                param["courseConsultantId"] = vm.searchCondition.courseConsultant.id;
            }
            CustomerCommunicationSchedule.query(param, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                $scope.pagination.totalItems = headers('X-Total-Count');
                // vm.queryCount = vm.totalItems;
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

        $rootScope.$on("reload_schedule", function (event) {
            vm.loadAll();
        });

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
