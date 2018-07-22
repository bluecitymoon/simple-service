(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$interval', '$timeout', 'MarketingNewOrderPlan', 'Customer', 'CustomerCommunicationSchedule', 'CustomerTrackTask'];

    function HomeController ($scope, Principal, LoginService, $state, $interval, $timeout, MarketingNewOrderPlan, Customer, CustomerCommunicationSchedule, CustomerTrackTask) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.overview = {};
        vm.register = register;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        Customer.getOverview({}, function (data) {
            vm.overview = data;
        });

        CustomerCommunicationSchedule.getSchedulesToday({}, function (data) {
            vm.customerCommunicationSchedules = data;
        });

        CustomerTrackTask.getCurrentUserCustomerTrackTasksToday({}, function (data) {
            vm.customerTrackTasks = data;
        });

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
                data: []
            },
            series: [
                {
                    name: '实际数量',
                    type: 'bar',
                    data: []
                },
                {
                    name: '目标数量',
                    type: 'bar',
                    data: []
                }
            ]
        };
        
        function reloadChart(plans) {

            $scope.lineOption.xAxis.data = [];
            $scope.lineOption.series[0].data = [];
            $scope.lineOption.series[1].data = [];

            angular.forEach(plans, function (i) {
                $scope.lineOption.xAxis.data.push(i.user.firstName);
                $scope.lineOption.series[0].data.push(i.currentNumber);
                $scope.lineOption.series[1].data.push(i.targetNumber);
                console.log(i);
            });
        }
        function loadPwiReport() {

            var currentTime = new Date();
            var parameters = {
                page: 0,
                size: 100,
                'year.equals': currentTime.getFullYear(),
                'month.equals': currentTime.getMonth() + 1
            };
            MarketingNewOrderPlan.query(parameters, function (response) {

                reloadChart(response);

            }, function (error) {
                console.log(error);

            });
        }

        loadPwiReport();

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

        vm.regenerateReport = function () {

            var currentTime = new Date();
            var parameters = {
                'year.equals': currentTime.getFullYear(),
                'month.equals': currentTime.getMonth() + 1
            };

            MarketingNewOrderPlan.generate(parameters, function (response) {
                reloadChart(response);
            }, function (error) {
                console.log(error);

            });
        };

    }
})();
