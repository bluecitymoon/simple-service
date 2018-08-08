(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentController', StudentController);

    StudentController.$inject = ['$state', 'Student', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', '$scope'];

    function StudentController($state, Student, ParseLinks, AlertService, paginationConstants, pagingParams, $scope) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        vm.clearConditions = function () {
            vm.searchCondition = {};
        };

        $scope.pagination = {
            currentPageNumber: 1,
            totalItems: 0
        };
        vm.loadAll = function () {

            var parameters = {
                page: $scope.pagination.currentPageNumber - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };

            if (vm.searchCondition.name) {
                parameters["name.contains"] = vm.searchCondition.name;
            }
            if (vm.searchCondition.customerName) {
                parameters["customerName"] = vm.searchCondition.customerName;
            }

            if (vm.searchCondition.customerPhoneNumber) {
                parameters["customerPhoneNumber"] = vm.searchCondition.customerPhoneNumber;
            }

            Student.query(parameters, onSuccess, onError);

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
                vm.students = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
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
