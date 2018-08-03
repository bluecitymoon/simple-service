(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('visted-customer-status', {
            parent: 'entity',
            url: '/visted-customer-status?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.vistedCustomerStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/visted-customer-status/visted-customer-statuses.html',
                    controller: 'VistedCustomerStatusController',
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
                    $translatePartialLoader.addPart('vistedCustomerStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('visted-customer-status-detail', {
            parent: 'visted-customer-status',
            url: '/visted-customer-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.vistedCustomerStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/visted-customer-status/visted-customer-status-detail.html',
                    controller: 'VistedCustomerStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vistedCustomerStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VistedCustomerStatus', function($stateParams, VistedCustomerStatus) {
                    return VistedCustomerStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'visted-customer-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('visted-customer-status-detail.edit', {
            parent: 'visted-customer-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/visted-customer-status/visted-customer-status-dialog.html',
                    controller: 'VistedCustomerStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VistedCustomerStatus', function(VistedCustomerStatus) {
                            return VistedCustomerStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('visted-customer-status.new', {
            parent: 'visted-customer-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/visted-customer-status/visted-customer-status-dialog.html',
                    controller: 'VistedCustomerStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                code: null,
                                comments: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('visted-customer-status', null, { reload: 'visted-customer-status' });
                }, function() {
                    $state.go('visted-customer-status');
                });
            }]
        })
        .state('visted-customer-status.edit', {
            parent: 'visted-customer-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/visted-customer-status/visted-customer-status-dialog.html',
                    controller: 'VistedCustomerStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VistedCustomerStatus', function(VistedCustomerStatus) {
                            return VistedCustomerStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('visted-customer-status', null, { reload: 'visted-customer-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('visted-customer-status.delete', {
            parent: 'visted-customer-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/visted-customer-status/visted-customer-status-delete-dialog.html',
                    controller: 'VistedCustomerStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VistedCustomerStatus', function(VistedCustomerStatus) {
                            return VistedCustomerStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('visted-customer-status', null, { reload: 'visted-customer-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
