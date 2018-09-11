(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerStatusReportDtlController', CustomerStatusReportDtlController);

    CustomerStatusReportDtlController.$inject = ['$scope', '$state', 'CustomerStatusReportDtl', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function CustomerStatusReportDtlController($scope, $state, CustomerStatusReportDtl, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.years = [];
        vm.months = [];
        vm.datePickerOpenStatus = {};
        vm.searchCondition = {
            yearObject: {id: new Date().getFullYear(), label: new Date().getFullYear()},
            monthObject: {id: new Date().getMonth(), label: new Date().getMonth()}
        };
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.loadAll = loadAll;

        initData();
        // loadAll();

        function initData() {

            for (var i = 2018; i < 3018; i ++) {
                vm.years.push({id : i, label: i});
            }

            for (var j = 1; j < 13; j ++) {
                vm.months.push({id : j, label: j});
            }

            var year = new Date().getFullYear();
            var month = new Date().getMonth() + 1;

            var thisYear = vm.years.filter(function (y) {
                return y.id == year
            })[0];

            var thisMonth = vm.months.filter(function (m) {
                return m.id == month
            })[0];

            vm.searchCondition.yearObject = thisYear;
            vm.searchCondition.monthObject = thisMonth;

        }


        function reloadChart() {

            var dataSeries = [];
            angular.forEach(vm.customerStatusReportDtls, function (element) {
                $scope.lineOption.legend.data.push(element.userName);

                var singleElement = {
                    name: element.userName,
                    type: 'bar',
                    barGap: 0,
                    label: labelOption,
                    data: [element.totalCount, element.newCreatedCount, element.dealedCount, element.errorInformation, element.scheduledCount, element.consideringCount]
                };

                dataSeries.push(singleElement);
            });

            $scope.lineOption.series = dataSeries;

        }

        function loadAll (type) {

            vm.searchCondition.queryType = type;

            if (vm.searchCondition.yearObject) {
                vm.searchCondition.year = vm.searchCondition.yearObject.id;
            }

            if (vm.searchCondition.monthObject) {
                vm.searchCondition.month = vm.searchCondition.monthObject.id;
            }

            CustomerStatusReportDtl.getStatusReport(vm.searchCondition, onSuccess, onError);
            CustomerStatusReportDtl.getLocationStatusReport(vm.searchCondition,
                function (data) {
                    vm.locationCustomerStatusReportDtls = data;
                }, onError);

            function onSuccess(data, headers) {
                vm.customerStatusReportDtls = data.data;
                $scope.chartdtls = data.chart;

                reloadChart();

                angular.forEach($scope.chartdtls, function (chart) {

                    chart.lineOption = {
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            },
                            position:function(p){
                                return [p[0] + 10, p[1] - 250];
                            }
                        },
                        legend: {
                            data: []
                        },
                        toolbox: {
                            show: true,
                            orient: 'vertical',
                            left: 'right',
                            top: 'center',
                            feature: {
                                // mark: {show: true},
                                // dataView: {show: true, readOnly: false},
                                // magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                                // restore: {show: true},
                                // saveAsImage: {show: true}
                            }
                        },
                        calculable: true,
                        xAxis: [
                            {
                                type: 'category',
                                axisTick: {show: false},
                                data: chart.xValues
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: "",
                                type: 'bar',
                                barGap: 0,
                                label: labelOption,
                                data: chart.yValues
                            }
                        ]
                    };
                });

                console.log(vm.chartdtls);

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

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }


        $scope.lineConfig = {
            theme:'shine',
            // event: [{click:onClick}],
            dataLoaded:true
        };

        var labelOption = {
            normal: {
                show: true,
                position: "insideBottom",
                distance: 0,
                align: "left",
                verticalAlign: "middle",
                rotate: 180,
                // formatter: '{c}',
                fontSize: 12,
                rich: {
                    name: {
                        textBorderColor: '#fff'
                    }
                }
            }
        };

        $scope.lineOption = {
            // color: ['#003366', '#006699', '#4cabce', '#e5323e'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                },
                position:function(p){
                    return [p[0] + 10, p[1] - 250];
                }
            },
            legend: {
                data: []
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    axisTick: {show: false},
                    data: ['总计', '新单', '成交', '信息错误', '已预约', '考虑中']
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: []
        };
    }
})();
