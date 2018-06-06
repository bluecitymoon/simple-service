(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerController', CustomerController);

    CustomerController.$inject = ['$state', '$stateParams', 'Customer', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'MarketChannelCategory'];

    function CustomerController($state, $stateParams, Customer, ParseLinks, AlertService, paginationConstants, pagingParams, MarketChannelCategory) {

        var vm = this;

        vm.department = $stateParams.dept;

        console.log(vm.department);

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.searchCondition = {};
        vm.channels = MarketChannelCategory.query();
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;

        vm.yesOrNo = [
            {id : 1, value: "已到访", visited: true},
            {id : 2, value: "未到访", visited: false}
        ];
        vm.classLevels = [
            {id: 1, value: "成年"},
            {id: 2, value: "学生"},
            {id: 3, value: "幼儿"}
        ];
        vm.clearConditions = function () {
            vm.searchCondition = {};
        };

        vm.loadAll = function() {
            var parameters = {
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };

            parameters["department"] = vm.department;
            
            if (vm.searchCondition.name) {
                parameters["name.contains"] = vm.searchCondition.name;
            }

            if (vm.searchCondition.contactPhoneNumber) {
                parameters["contactPhoneNumber.contains"] = vm.searchCondition.contactPhoneNumber;
            }

            if (vm.searchCondition.channel) {
                parameters["channelId.equals"] = vm.searchCondition.channel.id;
            }

            if (vm.searchCondition.classLevel) {
                parameters["classLevel.equals"] = vm.searchCondition.classLevel.value;
            }

            if (vm.searchCondition.visited) { //equal logic for department operation
                parameters["department"] = "operation";
            }

            if (vm.searchCondition.createStartDate) {
                parameters["createdDate.greaterOrEqualThan"] = vm.searchCondition.createStartDate;
            }
            if (vm.searchCondition.createEndDate) {
                parameters["createdDate.lessOrEqualThan"] = vm.searchCondition.createEndDate;
            }

            Customer.queryWithLog(parameters, onSuccess, onError);

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
                vm.customers = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
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

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
