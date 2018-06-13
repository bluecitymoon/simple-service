(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-track-task', {
            parent: 'entity',
            url: '/customer-track-task?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerTrackTask.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-track-task/customer-track-tasks.html',
                    controller: 'CustomerTrackTaskController',
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
                    $translatePartialLoader.addPart('customerTrackTask');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-track-task-detail', {
            parent: 'customer-track-task',
            url: '/customer-track-task/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerTrackTask.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-track-task/customer-track-task-detail.html',
                    controller: 'CustomerTrackTaskDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerTrackTask');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerTrackTask', function($stateParams, CustomerTrackTask) {
                    return CustomerTrackTask.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-track-task',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-track-task-detail.edit', {
            parent: 'customer-track-task-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-track-task/customer-track-task-dialog.html',
                    controller: 'CustomerTrackTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerTrackTask', function(CustomerTrackTask) {
                            return CustomerTrackTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-track-task.new', {
            parent: 'customer-track-task',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-track-task/customer-track-task-dialog.html',
                    controller: 'CustomerTrackTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-track-task', null, { reload: 'customer-track-task' });
                }, function() {
                    $state.go('customer-track-task');
                });
            }]
        })
        .state('customer-track-task.edit', {
            parent: 'customer-track-task',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-track-task/customer-track-task-dialog.html',
                    controller: 'CustomerTrackTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerTrackTask', function(CustomerTrackTask) {
                            return CustomerTrackTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-track-task', null, { reload: 'customer-track-task' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-track-task.delete', {
            parent: 'customer-track-task',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-track-task/customer-track-task-delete-dialog.html',
                    controller: 'CustomerTrackTaskDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerTrackTask', function(CustomerTrackTask) {
                            return CustomerTrackTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-track-task', null, { reload: 'customer-track-task' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
