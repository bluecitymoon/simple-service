(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('new-order-assign-history', {
            parent: 'entity',
            url: '/new-order-assign-history?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.newOrderAssignHistory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/new-order-assign-history/new-order-assign-histories.html',
                    controller: 'NewOrderAssignHistoryController',
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
                    $translatePartialLoader.addPart('newOrderAssignHistory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('new-order-assign-history-detail', {
            parent: 'new-order-assign-history',
            url: '/new-order-assign-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.newOrderAssignHistory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/new-order-assign-history/new-order-assign-history-detail.html',
                    controller: 'NewOrderAssignHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('newOrderAssignHistory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NewOrderAssignHistory', function($stateParams, NewOrderAssignHistory) {
                    return NewOrderAssignHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'new-order-assign-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('new-order-assign-history-detail.edit', {
            parent: 'new-order-assign-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-assign-history/new-order-assign-history-dialog.html',
                    controller: 'NewOrderAssignHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NewOrderAssignHistory', function(NewOrderAssignHistory) {
                            return NewOrderAssignHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('new-order-assign-history.new', {
            parent: 'new-order-assign-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-assign-history/new-order-assign-history-dialog.html',
                    controller: 'NewOrderAssignHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                olderFollowerLogin: null,
                                olderFollowerName: null,
                                newFollowerLogin: null,
                                newFollowerName: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('new-order-assign-history', null, { reload: 'new-order-assign-history' });
                }, function() {
                    $state.go('new-order-assign-history');
                });
            }]
        })
        .state('new-order-assign-history.edit', {
            parent: 'new-order-assign-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-assign-history/new-order-assign-history-dialog.html',
                    controller: 'NewOrderAssignHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NewOrderAssignHistory', function(NewOrderAssignHistory) {
                            return NewOrderAssignHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('new-order-assign-history', null, { reload: 'new-order-assign-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('new-order-assign-history.delete', {
            parent: 'new-order-assign-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-assign-history/new-order-assign-history-delete-dialog.html',
                    controller: 'NewOrderAssignHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NewOrderAssignHistory', function(NewOrderAssignHistory) {
                            return NewOrderAssignHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('new-order-assign-history', null, { reload: 'new-order-assign-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
