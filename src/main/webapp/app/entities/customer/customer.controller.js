(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerController', CustomerController);

    CustomerController.$inject = ['$uibModal', '$scope','$state', '$stateParams', 'Customer', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'MarketChannelCategory', 'User', 'NewOrderResourceLocation', 'TaskStatus', 'CustomerStatus', 'Principal', '$localStorage', 'CustomerCommunicationSchedule', 'Cache'];

    function CustomerController($uibModal, $scope, $state, $stateParams, Customer, ParseLinks, AlertService, paginationConstants, pagingParams, MarketChannelCategory, User, NewOrderResourceLocation, TaskStatus, CustomerStatus, Principal, $localStorage, CustomerCommunicationSchedule, Cache) {

        var vm = this;

        vm.department = $stateParams.dept;

        console.log(vm.department);

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.searchCondition = {};
        vm.channels = MarketChannelCategory.query({ page: 0,  size: 1000 });
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.taskstatuses = TaskStatus.query();
        vm.customerStatus = CustomerStatus.query();

        // vm.users = User.query({ page: 0,  size: 1000 });
        // vm.pwis = User.getAllPwis();
        vm.sales = User.getAllSales();
        vm.consultants = User.getAllCourseConsultant();
        vm.locations = NewOrderResourceLocation.query({ page: 0,  size: 1000 });
        vm.yesOrNo = [
            {id : 1, value: "已到访", visited: true},
            {id : 2, value: "未到访", visited: false}
        ];
        vm.classLevels = [
            {id: 1, value: "成年"},
            {id: 2, value: "学生"},
            {id: 3, value: "幼儿"}
        ];
        vm.ccAssignStatusList = [
            {name: "已分配", code: "assigned"},
            {name: "未分配", code: "not_assigned"}
        ];

        vm.salesAssignStatusList = [
            {name: "已分配", code: "assigned"},
            {name: "未分配", code: "not_assigned"}
        ];
        vm.clearConditions = function () {
            vm.searchCondition = {};
        };

        $scope.pagination = {
            currentPageNumber: 1,
            totalItems: 0
        };



        vm.allSelected = false;
        vm.loadAll = function() {
            var user = $localStorage.user;
            var isAdmin = false;
            angular.forEach(user.authorities, function (authority) {
                if (authority == 'ROLE_ADMIN') {
                    isAdmin = true;
                    return;
                }
            });

            //
            if (vm.department && vm.department == 'recipient' && angular.equals(vm.searchCondition, {}) && !isAdmin) {
                AlertService.warning("请输入搜索条件");
                return;
            }

            var parameters = {
                page: $scope.pagination.currentPageNumber - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };

            parameters["department"] = vm.department;
            
            if (vm.searchCondition.name) {
                parameters["name.contains"] = vm.searchCondition.name;
            }
            if (vm.searchCondition.trackResult) {
                parameters["trackStatus.equals"] = vm.searchCondition.trackResult.name;
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
            if (vm.searchCondition.nextTrackStartDate) {
                parameters["nextTrackDate.greaterOrEqualThan"] = vm.searchCondition.nextTrackStartDate;
            }
            if (vm.searchCondition.nextTrackEndDate) {
                parameters["nextTrackDate.lessOrEqualThan"] = vm.searchCondition.nextTrackEndDate;
            }

            if (vm.searchCondition.sales) {
                parameters["salesFollowerId.equals"] = vm.searchCondition.sales.id;
            }
            if (vm.searchCondition.courseConsultant) {
                parameters["courseConsultantId.equals"] = vm.searchCondition.courseConsultant.id;
            }
            if (vm.searchCondition.location) {
                parameters["locationId.equals"] = vm.searchCondition.location.id;
            }
            if (vm.searchCondition.customerStatus) {
                parameters["statusId.equals"] = vm.searchCondition.customerStatus.id;
            }

            if (vm.searchCondition.ccAssignStatus) {
                parameters["ccAssignStatus"] = vm.searchCondition.ccAssignStatus.code;
            }

            Cache.setCustomerSearchCondition(vm.searchCondition);

            Customer.query(parameters, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                $scope.pagination.totalItems = headers('X-Total-Count');
                // vm.queryCount = vm.totalItems;
                vm.customers = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        };
        reloadLastSearchPage();

        function reloadLastSearchPage() {
            var condition = Cache.getCustomerSearchCondition();

            if (condition) {
                vm.searchCondition = condition;

                vm.loadAll();
            }
        }
        vm.openCustomerEditDialog = function (customerId) {

            $uibModal.open({
                templateUrl: 'app/entities/customer/customer-dialog.html',
                controller: 'CustomerDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: ['Customer', function(Customer) {
                        return Customer.get({id : customerId}).$promise;
                    }]
                }
            }).result.then(function() {
                vm.loadAll();
            }, function() {
                // $state.go('^');
            });
        };

        vm.customerCheckin = function (customerId) {

            CustomerCommunicationSchedule.customersignin({id: customerId},
            function () {
                AlertService.success("签到成功!");
            },
            function () {
                AlertService.error("签到失败");
            })
        };

        vm.batchAssignNewOrder = function () {

            var selectedRecords = vm.customers.filter(function (r) {
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
                c.courseConsultant = vm.selectedUser;
                c.assignDate = new Date();
            });

            Customer.batchUpdate(selectedRecords, function (response) {

                AlertService.success("操作成功！批量分配了" + response.length + "条客户数据到用户" + vm.selectedUser.firstName+ "！");

            }, function (error) {

                AlertService.error(error);
            });

        };
        vm.toggleAll = function () {
            vm.customers.forEach(function (record) {
                record.selected = vm.allSelected;
            })
        };
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
