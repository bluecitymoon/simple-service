(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-card-upgrade-log', {
            parent: 'entity',
            url: '/customer-card-upgrade-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCardUpgradeLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-card-upgrade-log/customer-card-upgrade-logs.html',
                    controller: 'CustomerCardUpgradeLogController',
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
                    $translatePartialLoader.addPart('customerCardUpgradeLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-card-upgrade-log-detail', {
            parent: 'customer-card-upgrade-log',
            url: '/customer-card-upgrade-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCardUpgradeLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-card-upgrade-log/customer-card-upgrade-log-detail.html',
                    controller: 'CustomerCardUpgradeLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerCardUpgradeLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerCardUpgradeLog', function($stateParams, CustomerCardUpgradeLog) {
                    return CustomerCardUpgradeLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-card-upgrade-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-card-upgrade-log-detail.edit', {
            parent: 'customer-card-upgrade-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card-upgrade-log/customer-card-upgrade-log-dialog.html',
                    controller: 'CustomerCardUpgradeLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCardUpgradeLog', function(CustomerCardUpgradeLog) {
                            return CustomerCardUpgradeLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-card-upgrade-log.new', {
            parent: 'customer-card-upgrade-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card-upgrade-log/customer-card-upgrade-log-dialog.html',
                    controller: 'CustomerCardUpgradeLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                customerName: null,
                                customerId: null,
                                serialNumber: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-card-upgrade-log', null, { reload: 'customer-card-upgrade-log' });
                }, function() {
                    $state.go('customer-card-upgrade-log');
                });
            }]
        })
        .state('customer-card-upgrade-log.edit', {
            parent: 'customer-card-upgrade-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card-upgrade-log/customer-card-upgrade-log-dialog.html',
                    controller: 'CustomerCardUpgradeLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCardUpgradeLog', function(CustomerCardUpgradeLog) {
                            return CustomerCardUpgradeLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-card-upgrade-log', null, { reload: 'customer-card-upgrade-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-card-upgrade-log.delete', {
            parent: 'customer-card-upgrade-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card-upgrade-log/customer-card-upgrade-log-delete-dialog.html',
                    controller: 'CustomerCardUpgradeLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerCardUpgradeLog', function(CustomerCardUpgradeLog) {
                            return CustomerCardUpgradeLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-card-upgrade-log', null, { reload: 'customer-card-upgrade-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
