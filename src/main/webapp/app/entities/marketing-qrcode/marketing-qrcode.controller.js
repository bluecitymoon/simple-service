(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketingQrcodeController', MarketingQrcodeController);

    MarketingQrcodeController.$inject = ['$state', 'MarketingQrcode', 'User', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function MarketingQrcodeController($state, MarketingQrcode, User, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.createNewQrcode = function () {
            var agentId = vm.selectedCreatingUser.id;
            MarketingQrcode.generate({id: agentId});
        };
        vm.searchQrcodeBySelectedUser = function () {
        };

        loadAll();
        loadAllUsers();

        function loadAll () {
            MarketingQrcode.query({
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
                vm.marketingQrcodes = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadAllUsers () {
            User.query({
                page: 0,
                size: 1000
            }, onSuccess, onError);

            function onSuccess(data) {
                vm.users = data;
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
