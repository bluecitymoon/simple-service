(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CollectionController', CollectionController);

    CollectionController.$inject = ['$scope', '$state', 'Collection', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function CollectionController($scope, $state, Collection, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.searchCondition = {};
        $scope.pagination = {
            currentPageNumber: 1,
            totalItems: 0
        };

        vm.confirmCollection = function (collection) {
            Collection.confirm(collection, function () {
                AlertService.success("确定收款成功！");
            }, function () {
                AlertService.error("确实收款失败!");
            })
        };


        vm.loadAll = function loadAll() {
            var parameters = {
                page: $scope.pagination.currentPageNumber - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };

            if (vm.searchCondition.customerName) {
                parameters["customerName"] = vm.searchCondition.customerName;
            }
            if (vm.searchCondition.customerContactPhoneNumber) {
                parameters["customerContactPhoneNumber"] = vm.searchCondition.customerContactPhoneNumber;
            }
            if (vm.searchCondition.contractNumber) {
                parameters["contractNumber.equals"] = vm.searchCondition.contractNumber;
            }
            if (vm.searchCondition.serialNumber) {
                parameters["sequenceNumber.equals"] = vm.searchCondition.serialNumber;
            }

            Collection.query(parameters, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                // vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.collections = data;
                vm.page = pagingParams.page;
                $scope.pagination.totalItems = headers('X-Total-Count');
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        };

        vm.loadAll();

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
