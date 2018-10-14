(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardController', CustomerCardController);

    CustomerCardController.$inject = ['$scope', '$state', 'CustomerCard', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'CustomerCardType', '$uibModal'];

    function CustomerCardController($scope, $state, CustomerCard, ParseLinks, AlertService, paginationConstants, pagingParams, CustomerCardType, $uibModal) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.customercardtypes = CustomerCardType.query({ page: 0,  size: 1000 });
        vm.searchCondition = {};
        var currentPageNumber = 1;
        // var cacheCondition = Cache.getCustomerSearchCondition();
        //
        // if (cacheCondition) {
        //     currentPageNumber = cacheCondition.currentPageNumber;
        // }

        $scope.pagination = {
            currentPageNumber: currentPageNumber,
            totalItems: 0
        };
        vm.loadAll = loadAll;

        function loadAll () {

            var parameters = {
                page: $scope.pagination.currentPageNumber - 1,
                size: vm.itemsPerPage,
                sort: sort()
            };

            if (vm.searchCondition.name) {
                parameters["customerName"] = vm.searchCondition.name;
            }

            if (vm.searchCondition.contactPhoneNumber) {
                parameters["customerPhoneNumber"] = vm.searchCondition.contactPhoneNumber;
            }

            if (vm.searchCondition.cardType) {
                parameters["customerCardTypeId.equals"] = vm.searchCondition.cardType.id;
            }
            if (vm.searchCondition.number) {
                parameters["number.equals"] = vm.searchCondition.number;
            }
            if (vm.searchCondition.serialNumber) {
                parameters["serialNumber.equals"] = vm.searchCondition.serialNumber;
            }

            CustomerCard.query(parameters, onSuccess, onError);

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
                $scope.pagination.totalItems = headers('X-Total-Count');
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
