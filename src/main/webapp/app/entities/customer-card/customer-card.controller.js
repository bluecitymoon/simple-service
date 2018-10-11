(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardController', CustomerCardController);

    CustomerCardController.$inject = ['$state', 'CustomerCard', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'CustomerCardType', '$uibModal'];

    function CustomerCardController($state, CustomerCard, ParseLinks, AlertService, paginationConstants, pagingParams, CustomerCardType, $uibModal) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.customercardtypes = CustomerCardType.query({ page: 0,  size: 1000 });
        loadAll();

        function loadAll () {
            CustomerCard.query({
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
                vm.customerCards = data;
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

        vm.upgradeCard = function (card) {

            $uibModal.open({
                templateUrl: 'app/entities/customer-card/customer-card-upgrade-dialog.html',
                controller: 'CustomerCardDialogUpgradeController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: [function() {
                        return card;
                    }]
                }
            }).result.then(function() {
            }, function() {
            });
        }
    }
})();
