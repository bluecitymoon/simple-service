(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('consultant-report', {
            parent: 'entity',
            url: '/consultant-report?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.consultantReport.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/consultant-report/consultant-reports.html',
                    controller: 'ConsultantReportController',
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
                    $translatePartialLoader.addPart('consultantReport');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('consultant-report-detail', {
            parent: 'consultant-report',
            url: '/consultant-report/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.consultantReport.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/consultant-report/consultant-report-detail.html',
                    controller: 'ConsultantReportDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('consultantReport');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ConsultantReport', function($stateParams, ConsultantReport) {
                    return ConsultantReport.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'consultant-report',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('consultant-report-detail.edit', {
            parent: 'consultant-report-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consultant-report/consultant-report-dialog.html',
                    controller: 'ConsultantReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConsultantReport', function(ConsultantReport) {
                            return ConsultantReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('consultant-report.new', {
            parent: 'consultant-report',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consultant-report/consultant-report-dialog.html',
                    controller: 'ConsultantReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                weekName: null,
                                weekFromDate: null,
                                weekEndDate: null,
                                visitedCount: null,
                                dealedMoneyAmount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('consultant-report', null, { reload: 'consultant-report' });
                }, function() {
                    $state.go('consultant-report');
                });
            }]
        })
        .state('consultant-report.edit', {
            parent: 'consultant-report',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consultant-report/consultant-report-dialog.html',
                    controller: 'ConsultantReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConsultantReport', function(ConsultantReport) {
                            return ConsultantReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('consultant-report', null, { reload: 'consultant-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('consultant-report.delete', {
            parent: 'consultant-report',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consultant-report/consultant-report-delete-dialog.html',
                    controller: 'ConsultantReportDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ConsultantReport', function(ConsultantReport) {
                            return ConsultantReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('consultant-report', null, { reload: 'consultant-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
