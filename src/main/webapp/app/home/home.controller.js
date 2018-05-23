(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$interval', '$timeout'];

    function HomeController ($scope, Principal, LoginService, $state, $interval, $timeout) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        function onClick(params){
            console.log(params);
        };

        $scope.lineConfig = {
            theme:'shine',
            event: [{click:onClick}],
            dataLoaded:true
        };

        $scope.lineOption = {
            title: {
                text: 'PWI地推目标完成度',
                subtext: '报告生成时间:'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            legend: {
                data: ['实际数量', '目标数量']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, 0.01]
            },
            xAxis: {
                type: 'category',
                data: ['Jerry','Ada','Shelly','Mini','Tina','James']
            },
            series: [
                {
                    name: '实际数量',
                    type: 'bar',
                    data: [18203, 23489, 29034, 104970, 131744, 30230]
                },
                {
                    name: '目标数量',
                    type: 'bar',
                    data: [19325, 23438, 31000, 121594, 134141, 81807]
                }
            ]
        };

        vm.regenerateReport = function () {
            $scope.lineOption.series = [
                {
                    name: '实际数量',
                    type: 'bar',
                    data: [28203, 23489, 29034, 104970, 131744, 10230]
                },
                {
                    name: '目标数量',
                    type: 'bar',
                    data: [19325, 23438, 31000, 121594, 134141, 81807]
                }
            ];
        };

    }
})();
