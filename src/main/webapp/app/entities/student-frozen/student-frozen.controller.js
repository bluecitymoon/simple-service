(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentFrozenController', StudentFrozenController);

    StudentFrozenController.$inject = ['$uibModal', '$state', 'StudentFrozen', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function StudentFrozenController($uibModal, $state, StudentFrozen, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        vm.viewFrozenArrangements = function (studentFrozen) {
            $uibModal.open({
                templateUrl: 'app/entities/student-frozen/student-lock-list.html',
                controller: 'StudentLockListController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: [function() {
                        return studentFrozen;
                    }]
                }
            }).result.then(function() {
                // $state.go('^', {}, { reload: false });
            }, function() {
                // $state.go('^');
            });
        };

        loadAll();

        function loadAll () {
            StudentFrozen.query({
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
                vm.studentFrozens = data;
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
