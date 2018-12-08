(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-class-log-daily-report', {
            parent: 'entity',
            url: '/student-class-log-daily-report?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClassLogDailyReport.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class-log-daily-report/student-class-log-daily-reports.html',
                    controller: 'StudentClassLogDailyReportController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentClassLogDailyReport');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-class-log-daily-report-detail', {
            parent: 'student-class-log-daily-report',
            url: '/student-class-log-daily-report/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClassLogDailyReport.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class-log-daily-report/student-class-log-daily-report-detail.html',
                    controller: 'StudentClassLogDailyReportDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentClassLogDailyReport');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentClassLogDailyReport', function($stateParams, StudentClassLogDailyReport) {
                    return StudentClassLogDailyReport.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-class-log-daily-report',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-class-log-daily-report-detail.edit', {
            parent: 'student-class-log-daily-report-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log-daily-report/student-class-log-daily-report-dialog.html',
                    controller: 'StudentClassLogDailyReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClassLogDailyReport', function(StudentClassLogDailyReport) {
                            return StudentClassLogDailyReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class-log-daily-report.new', {
            parent: 'student-class-log-daily-report',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log-daily-report/student-class-log-daily-report-dialog.html',
                    controller: 'StudentClassLogDailyReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                shouldTaken: null,
                                leave: null,
                                absence: null,
                                added: null,
                                actualTaken: null,
                                logDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-class-log-daily-report', null, { reload: 'student-class-log-daily-report' });
                }, function() {
                    $state.go('student-class-log-daily-report');
                });
            }]
        })
        .state('student-class-log-daily-report.edit', {
            parent: 'student-class-log-daily-report',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log-daily-report/student-class-log-daily-report-dialog.html',
                    controller: 'StudentClassLogDailyReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClassLogDailyReport', function(StudentClassLogDailyReport) {
                            return StudentClassLogDailyReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class-log-daily-report', null, { reload: 'student-class-log-daily-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class-log-daily-report.delete', {
            parent: 'student-class-log-daily-report',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log-daily-report/student-class-log-daily-report-delete-dialog.html',
                    controller: 'StudentClassLogDailyReportDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentClassLogDailyReport', function(StudentClassLogDailyReport) {
                            return StudentClassLogDailyReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class-log-daily-report', null, { reload: 'student-class-log-daily-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
