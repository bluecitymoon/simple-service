(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerScheduleFeedbackController', CustomerScheduleFeedbackController);

    CustomerScheduleFeedbackController.$inject = ['$scope','$state', 'CustomerScheduleFeedback', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function CustomerScheduleFeedbackController($scope, $state, CustomerScheduleFeedback, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.searchCondition = {};
        vm.clearConditions = function () {
            vm.searchCondition = {};
        };

        $scope.pagination = {
            currentPageNumber: 1,
            totalItems: 0
        };
        vm.loadAll = loadAll;

        function loadAll () {

            var parameters = {
                page: $scope.pagination.currentPageNumber - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };


            if (vm.searchCondition.giftCode) {
                parameters["giftCode.equals"] = vm.searchCondition.giftCode;
            }

            if (vm.searchCondition.customerPhoneNumber) {
                parameters["customerPhoneNumber"] = vm.searchCondition.customerPhoneNumber;
            }

            CustomerScheduleFeedback.query(parameters, onSuccess, onError);
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
                $scope.pagination.totalItems = vm.totalItems;
                vm.customerScheduleFeedbacks = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.confirmGiftGiven = function (feedback) {

            feedback.giftStatus = '已领取';
            CustomerScheduleFeedback.update(feedback, function (response) {
                // AlertService.success("确认成功");
                loadAll();
            });
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
    }
})();
