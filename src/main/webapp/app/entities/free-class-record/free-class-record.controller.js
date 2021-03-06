(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FreeClassRecordController', FreeClassRecordController);

    FreeClassRecordController.$inject = ['$scope','$timeout', '$state', 'FreeClassRecord', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'User', 'MarketChannelCategory', 'NewOrderResourceLocation', 'FileUploader', 'Cache', 'CustomerStatus'];

    function FreeClassRecordController($scope, $timeout, $state, FreeClassRecord, ParseLinks, AlertService, paginationConstants, pagingParams, User, MarketChannelCategory, NewOrderResourceLocation, FileUploader, Cache, CustomerStatus) {

        var vm = this;

        vm.selectedUser = null;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        // vm.pageOptions = [];
        vm.searchCondition = {};
        vm.loadAll = loadAll;
        // vm.loadAll();
        vm.allSelected = false;
        vm.datePickerOpenStatus = {};
        vm.channels = MarketChannelCategory.query({ page: 0,  size: 1000 });
        vm.locations = NewOrderResourceLocation.query({ page: 0,  size: 1000 });
        vm.pwis = User.getAllPwiWithOuterUser();
        vm.sales = User.getAllSales();
        vm.showUploadTextArea = false;
        vm.customerStatus = CustomerStatus.query();
        vm.sourceTypes = [
            {id: 1, name: "小程序", code: "WeChat"},
            {id: 2, name: "非小程序", code: null}
        ];
        vm.salesAssignStatusList = [

            {name: "已分配", code: "assigned"},
            {name: "未分配", code: "not_assigned"}
        ];


        vm.openCalendar = openCalendar;
        vm.datePickerOptions = {             showMeridian: false         };
        vm.datePickerOptions = {
            showMeridian: false
        };
        vm.clearConditions = function () {
            vm.searchCondition = {};
        };


        var uploader = $scope.uploader = new FileUploader({
            url: 'api/free-class-records/upload/byfile'
        });

        vm.batchUploadNewOrders = function () {

        };

        vm.datePickerOptions = {
            showMeridian: false
        };

        vm.batchAssignNewOrder = function () {
            var selectedRecords = vm.freeClassRecords.filter(function (r) {
                return r.selected;
            });

            if (!selectedRecords || selectedRecords.length == 0) {
                AlertService.error("没有选中任何新单，无法分配！");
                return;
            }

            if (!vm.selectedUser) {
                AlertService.error("请选择目标用户！");
                return;
            }

            selectedRecords.forEach(function (newOrder) {
                newOrder.salesFollower = vm.selectedUser;
            });

            FreeClassRecord.batchUpdate(selectedRecords, function (response) {

                AlertService.success("操作成功！批量分配了" + response.length + "条新单到用户" + vm.selectedUser.firstName+ "！");

            }, function (error) {

                AlertService.showCommonError(error);
            });

        };

        vm.batchAnalysis = function () {

            vm.showUploadTextArea = !vm.showUploadTextArea;
        };
        vm.batchUpload = function () {

            FreeClassRecord.batchUploadCustomers({content: vm.textUpload}, function (response) {
                console.log(response);
            }, function (error) {
                console.log(error);
            })
        };

        loadAllUsers();

        function loadAllUsers () {
            User.getAllSales({}, onSuccess, onError);

            function onSuccess(data) {
                vm.users = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.toggleAll = function () {
            // vm.allSelected = !vm.allSelected;
            vm.freeClassRecords.forEach(function (record) {
                record.selected = vm.allSelected;
            })
        };

        $scope.pagination = {
            currentPageNumber: 1,
            totalItems: 0
        };

        function loadAll () {

            vm.parameters = {
                page: $scope.pagination.currentPageNumber - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };

            if (vm.searchCondition.name) {
                vm.parameters["personName.contains"] = vm.searchCondition.name;
            }

            if (vm.searchCondition.contactPhoneNumber) {
                vm.parameters["contactPhoneNumber.contains"] = vm.searchCondition.contactPhoneNumber;
            }

            if (vm.searchCondition.channel) {
                vm.parameters["marketChannelCategoryId.equals"] = vm.searchCondition.channel.id;
            }

            if (vm.searchCondition.startDate) {
                vm.parameters["createdDate.greaterOrEqualThan"] = vm.searchCondition.startDate;
            }
            if (vm.searchCondition.endDate) {
                vm.parameters["createdDate.lessOrEqualThan"] = vm.searchCondition.endDate;
            }

            if (vm.searchCondition.sales) {
                vm.parameters["salesFollowerId.equals"] = vm.searchCondition.sales.id;
            }

            console.log(vm.searchCondition);
            if (vm.searchCondition.pwi) {
                if (vm.searchCondition.pwi.id) {
                    vm.parameters["agentId.equals"] = vm.searchCondition.pwi.id;
                } else {
                    vm.parameters["outerReferer.contains"] = vm.searchCondition.pwi.firstName;
                }
            }

            if (vm.searchCondition.location) {
                vm.parameters["locationId.equals"] = vm.searchCondition.location.id;
            }

            if (vm.searchCondition.salesAssignStatus) {
                vm.parameters["salesFollowerAssignStatus"] = vm.searchCondition.salesAssignStatus.code;
            }

            if (vm.searchCondition.customerStatus) {
                vm.parameters["status.equals"] = vm.searchCondition.customerStatus.name;
            }
            if (vm.searchCondition.sourceType) {
                vm.parameters["sourceType.equals"] = vm.searchCondition.sourceType.code;
            }
            FreeClassRecord.query(vm.parameters, onSuccess, onError);


            Cache.setNewOrderCondition(vm.searchCondition);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                $scope.pagination.totalItems = headers('X-Total-Count');
                // vm.queryCount = vm.totalItems;
                vm.freeClassRecords = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadLastSearchResult() {
            if (Cache.getNewOrderCondition()) {
                vm.searchCondition = Cache.getNewOrderCondition();

                vm.loadAll();
            }
        }

        loadLastSearchResult();

        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }
        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, vm.parameters);
        }
        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
