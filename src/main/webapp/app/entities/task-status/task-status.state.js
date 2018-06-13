(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('task-status', {
            parent: 'entity',
            url: '/task-status?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.taskStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/task-status/task-statuses.html',
                    controller: 'TaskStatusController',
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
                    $translatePartialLoader.addPart('taskStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('task-status-detail', {
            parent: 'task-status',
            url: '/task-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.taskStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/task-status/task-status-detail.html',
                    controller: 'TaskStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taskStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TaskStatus', function($stateParams, TaskStatus) {
                    return TaskStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'task-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('task-status-detail.edit', {
            parent: 'task-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-status/task-status-dialog.html',
                    controller: 'TaskStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TaskStatus', function(TaskStatus) {
                            return TaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('task-status.new', {
            parent: 'task-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-status/task-status-dialog.html',
                    controller: 'TaskStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                code: null,
                                labelCss: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('task-status', null, { reload: 'task-status' });
                }, function() {
                    $state.go('task-status');
                });
            }]
        })
        .state('task-status.edit', {
            parent: 'task-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-status/task-status-dialog.html',
                    controller: 'TaskStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TaskStatus', function(TaskStatus) {
                            return TaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('task-status', null, { reload: 'task-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('task-status.delete', {
            parent: 'task-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-status/task-status-delete-dialog.html',
                    controller: 'TaskStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TaskStatus', function(TaskStatus) {
                            return TaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('task-status', null, { reload: 'task-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
