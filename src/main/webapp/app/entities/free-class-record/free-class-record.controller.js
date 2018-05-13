(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FreeClassRecordController', FreeClassRecordController);

    FreeClassRecordController.$inject = ['$state', 'FreeClassRecord', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function FreeClassRecordController($state, FreeClassRecord, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        // vm.pageOptions = [];
        vm.loadAll = loadAll;
        vm.loadAll();
        vm.allSelected = false;
        vm.batchAssignNewOrder = function () {
            var selectedRecords = vm.freeClassRecords.filter(function (r) {
                return r.selected;
            });

            if (!selectedRecords || selectedRecords.length == 0) {
                AlertService.error("没有选中任何新单，无法分配！");
                return;
            }

        };
        vm.toggleAll = function () {
            // vm.allSelected = !vm.allSelected;
            vm.freeClassRecords.forEach(function (record) {
                record.selected = vm.allSelected;
            })
        };

        function loadAll () {
            FreeClassRecord.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
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
                vm.freeClassRecords = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

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
