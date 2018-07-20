(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-status-report-dtl', {
            parent: 'entity',
            url: '/customer-status-report-dtl?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerStatusReportDtl.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-status-report-dtl/customer-status-report-dtls.html',
                    controller: 'CustomerStatusReportDtlController',
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
                    $translatePartialLoader.addPart('customerStatusReportDtl');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-status-report-dtl-detail', {
            parent: 'customer-status-report-dtl',
            url: '/customer-status-report-dtl/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerStatusReportDtl.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-status-report-dtl/customer-status-report-dtl-detail.html',
                    controller: 'CustomerStatusReportDtlDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerStatusReportDtl');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerStatusReportDtl', function($stateParams, CustomerStatusReportDtl) {
                    return CustomerStatusReportDtl.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-status-report-dtl',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-status-report-dtl-detail.edit', {
            parent: 'customer-status-report-dtl-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-status-report-dtl/customer-status-report-dtl-dialog.html',
                    controller: 'CustomerStatusReportDtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerStatusReportDtl', function(CustomerStatusReportDtl) {
                            return CustomerStatusReportDtl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-status-report-dtl.new', {
            parent: 'customer-status-report-dtl',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-status-report-dtl/customer-status-report-dtl-dialog.html',
                    controller: 'CustomerStatusReportDtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                userName: null,
                                ageTooSmallCount: null,
                                errorInformation: null,
                                noWillingCount: null,
                                consideringCount: null,
                                scheduledCount: null,
                                dealedCount: null,
                                newCreatedCount: null,
                                totalCount: null,
                                finishRate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-status-report-dtl', null, { reload: 'customer-status-report-dtl' });
                }, function() {
                    $state.go('customer-status-report-dtl');
                });
            }]
        })
        .state('customer-status-report-dtl.edit', {
            parent: 'customer-status-report-dtl',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-status-report-dtl/customer-status-report-dtl-dialog.html',
                    controller: 'CustomerStatusReportDtlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerStatusReportDtl', function(CustomerStatusReportDtl) {
                            return CustomerStatusReportDtl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-status-report-dtl', null, { reload: 'customer-status-report-dtl' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-status-report-dtl.delete', {
            parent: 'customer-status-report-dtl',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-status-report-dtl/customer-status-report-dtl-delete-dialog.html',
                    controller: 'CustomerStatusReportDtlDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerStatusReportDtl', function(CustomerStatusReportDtl) {
                            return CustomerStatusReportDtl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-status-report-dtl', null, { reload: 'customer-status-report-dtl' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
