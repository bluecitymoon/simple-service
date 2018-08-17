(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentController', StudentController);

    StudentController.$inject = ['$state', 'Student', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', '$scope', 'Product'];

    function StudentController($state, Student, ParseLinks, AlertService, paginationConstants, pagingParams, $scope, Product) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.searchCondition = {};
        vm.students = [];
        vm.allSelected = false;

        vm.clearConditions = function () {
            vm.searchCondition = {};
        };
        vm.classes = Product.query({ page: 0,  size: 1000 });
        vm.toggleAll = function () {
            angular.forEach(vm.students, function (student) {
                student.selected = vm.allSelected;
            });
            // vm.students.forEach(function (record) {
            //     record.selected = vm.allSelected;
            // })
        };
        vm.batchAssignStudentIntoClass = function () {

            var selectedRecords = vm.students.filter(function (r) {
                return r.selected;
            });

            if (!selectedRecords || selectedRecords.length == 0) {
                AlertService.error("请选择目标学员！");
                return;
            }

            if (!vm.selectedClass) {
                AlertService.error("请选择目标班级！");
                return;
            }
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
                vm.allSelected = false;
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
