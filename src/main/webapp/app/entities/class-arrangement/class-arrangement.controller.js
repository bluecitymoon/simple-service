(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementController', ClassArrangementController);

    ClassArrangementController.$inject = ['$uibModal', '$scope', '$state', 'ClassArrangement', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams', 'uiCalendarConfig'];

    function ClassArrangementController($uibModal, $scope, $state, ClassArrangement, ParseLinks, AlertService, paginationConstants, pagingParams, uiCalendarConfig) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.years = [];
        vm.months = [];
        vm.searchCondition = {};
        $scope.events = [];
        $scope.calendar = {};
        vm.datePickerOpenStatus = {};

        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1;

        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.searchCalendars = function (type) {
            vm.searchCondition.queryType = type;

            if (vm.searchCondition.yearObject) {
                vm.searchCondition.year = vm.searchCondition.yearObject.id;
            }

            if (vm.searchCondition.monthObject) {
                vm.searchCondition.month = vm.searchCondition.monthObject.id;
            }

            ClassArrangement.searchSchedulesInRange(vm.searchCondition, function (data) {
                vm.schedules = data;
            })
        };

        $scope.eventSources = [
            {
                events: $scope.events
            }
        ];
        angular.element('.calendar').fullCalendar('option', 'timezone', 'local');

        initData();

        function loadSchedulesThisMonth() {

            var request = {
                year: year,
                month: month,
                queryType: 'monthly'
            };

            ClassArrangement.searchSchedulesInRange(request, function (data) {

                angular.forEach(data, function (schedule) {

                    schedule.id = schedule.classId;

                    $scope.events.push(schedule);
                });
            })
        }

        function loadClassesThisWeek() {
            ClassArrangement.getArrangementsInCurrentWeek({}, function (data) {
                vm.weekClasses = data;
            })
        }

        loadClassesThisWeek();
        //loadSchedulesThisMonth();

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

        $scope.$on("simpleServiceApp:classArrangementsGenerated", function (event) {
            loadClassesThisWeek();
        });

        vm.todayClassArrangments = [];
        vm.openStudentsManagementDialog = function (classArrangement) {

            if (!classArrangement.arrangementId) {
                AlertService.info("这个时间段没有排班。");
                return;
            }

            $uibModal.open({
                templateUrl: 'app/entities/class-arrangement/student-mgr-dialog.html',
                controller: 'StudentMgrDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    entity: function () {
                        return classArrangement;
                    }
                }
            }).result.then(function() {
            }, function() {
            });
        };

        vm.openClassSignDialog = function (classArrangement) {

            if (!classArrangement.arrangementId) {
                // AlertService.info("这个时间段没有排班。");
                return;
            }

            $uibModal.open({
                templateUrl: 'app/entities/class-arrangement/student-sign-dialog.html',
                controller: 'StudentSignDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return classArrangement;
                    }
                }
            }).result.then(function() {
            }, function() {
            });
        };

        vm.createNewClassArrangements = function (mf, startHour, endHour, index, weekday) {

            var classroomId = vm.weekClasses[0].classrooms[index].id;
            if (!mf.id) {
                mf.classroomId = classroomId;
            }

            var schedule = {
                classSchedule: mf,
                start: startHour,
                end: endHour,
                index: index,
                weekdayName: weekday
            };

            $scope.dialogSchedule = schedule;

            $uibModal.open({
                templateUrl: 'app/entities/class-arrangement/single-class-arrangement-dialog.controller.html',
                controller: 'SingleClassArrangmentDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                scope: $scope
            }).result.then(function() {
            }, function() {
            });
        };

        vm.studentLeaveRequest = function (classArrangement) {
            if (!classArrangement.arrangementId) {
                // AlertService.info("这个时间段没有排班。");
                return;
            }

            $uibModal.open({
                templateUrl: 'app/entities/class-arrangement/student-leave-dialog.html',
                controller: 'StudentSignLeaveController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return classArrangement;
                    }
                }
            }).result.then(function() {
            }, function() {
            });
        };

        function getArrangementsToday() {
            ClassArrangement.getClassArrangementsToday({}, function (data) {
                vm.todayClassArrangments = data;

            })
        }

        $scope.showStudentsInClass = function (date, jsEvent, view) {

            vm.openClassSignDialog(date);
        };

        $scope.uiConfig = {
            calendar: {
                height: 450,
                editable: false,
                header: {
                    right: 'month basicWeek'
                },
                eventClick: $scope.showStudentsInClass,
                timezone: 'Asia/Shanghai',
                lang: 'zh-cn'
            }
        };
        //basicDay agendaWeek agendaDay

        getArrangementsToday();

        // loadAll();
        //
        // function loadAll() {
        //     ClassArrangement.query({
        //         page: pagingParams.page - 1,
        //         size: vm.itemsPerPage,
        //         sort: sort()
        //     }, onSuccess, onError);
        //     function sort() {
        //         var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
        //         if (vm.predicate !== 'id') {
        //             result.push('id');
        //         }
        //         return result;
        //     }
        //
        //     function onSuccess(data, headers) {
        //         vm.links = ParseLinks.parse(headers('link'));
        //         vm.totalItems = headers('X-Total-Count');
        //         vm.queryCount = vm.totalItems;
        //         vm.classArrangements = data;
        //         vm.page = pagingParams.page;
        //     }
        //
        //     function onError(error) {
        //         AlertService.error(error.data.message);
        //     }
        // }

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
