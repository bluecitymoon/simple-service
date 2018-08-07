(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-collection-log', {
            parent: 'entity',
            url: '/customer-collection-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCollectionLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-collection-log/customer-collection-logs.html',
                    controller: 'CustomerCollectionLogController',
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
                    $translatePartialLoader.addPart('customerCollectionLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-collection-log-detail', {
            parent: 'customer-collection-log',
            url: '/customer-collection-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCollectionLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-collection-log/customer-collection-log-detail.html',
                    controller: 'CustomerCollectionLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerCollectionLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerCollectionLog', function($stateParams, CustomerCollectionLog) {
                    return CustomerCollectionLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-collection-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-collection-log-detail.edit', {
            parent: 'customer-collection-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-collection-log/customer-collection-log-dialog.html',
                    controller: 'CustomerCollectionLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCollectionLog', function(CustomerCollectionLog) {
                            return CustomerCollectionLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-collection-log.new', {
            parent: 'customer-collection-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-collection-log/customer-collection-log-dialog.html',
                    controller: 'CustomerCollectionLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                serialNumber: null,
                                moneyShouldCollected: null,
                                moneyCollected: null,
                                balance: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-collection-log', null, { reload: 'customer-collection-log' });
                }, function() {
                    $state.go('customer-collection-log');
                });
            }]
        })
        .state('customer-collection-log.edit', {
            parent: 'customer-collection-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-collection-log/customer-collection-log-dialog.html',
                    controller: 'CustomerCollectionLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCollectionLog', function(CustomerCollectionLog) {
                            return CustomerCollectionLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-collection-log', null, { reload: 'customer-collection-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-collection-log.delete', {
            parent: 'customer-collection-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-collection-log/customer-collection-log-delete-dialog.html',
                    controller: 'CustomerCollectionLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerCollectionLog', function(CustomerCollectionLog) {
                            return CustomerCollectionLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-collection-log', null, { reload: 'customer-collection-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
