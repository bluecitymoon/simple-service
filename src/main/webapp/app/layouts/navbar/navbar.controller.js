(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope','$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'Region', 'AlertService', '$localStorage'];

    function NavbarController ($scope, $state, Auth, Principal, ProfileService, LoginService, Region, AlertService, $localStorage) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        vm.account = null;
        vm.login = LoginService.open;

        vm.regions = [];
        vm.selectedRegion = {};

        loadUserRegions();
        vm.selectRegion = function (region) {

            if (region.id == vm.selectedRegion.id) {
                return;
            }

            storeUserRegion(region);
        };
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function loadUserRegions() {
            Region.getCurrentUserRegions({}, function (regions) {
                vm.regions = regions;

                if (vm.regions.length == 0) {
                    AlertService.warn("用户没有被分配到任何校区，使用系统时可能出现意外问题!");
                } else if (vm.regions.length == 1) {
                    storeUserRegion(vm.regions[0]);
                } else {
                    storeUserRegion(vm.regions[1]);
                }
            }, function (error) {
                AlertService.error('加载用户所在区域出错！');
            });
        }

        function storeUserRegion(region) {

            AlertService.info("当前校区为：" + region.name + "校区");

            $localStorage.currentUserRegion = region;
            vm.selectedRegion = region;
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
