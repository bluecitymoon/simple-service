(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scheduled-task-log', {
            parent: 'entity',
            url: '/scheduled-task-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.scheduledTaskLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scheduled-task-log/scheduled-task-logs.html',
                    controller: 'ScheduledTaskLogController',
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
                    $translatePartialLoader.addPart('scheduledTaskLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scheduled-task-log-detail', {
            parent: 'scheduled-task-log',
            url: '/scheduled-task-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.scheduledTaskLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scheduled-task-log/scheduled-task-log-detail.html',
                    controller: 'ScheduledTaskLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scheduledTaskLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ScheduledTaskLog', function($stateParams, ScheduledTaskLog) {
                    return ScheduledTaskLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'scheduled-task-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('scheduled-task-log-detail.edit', {
            parent: 'scheduled-task-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheduled-task-log/scheduled-task-log-dialog.html',
                    controller: 'ScheduledTaskLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ScheduledTaskLog', function(ScheduledTaskLog) {
                            return ScheduledTaskLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scheduled-task-log.new', {
            parent: 'scheduled-task-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheduled-task-log/scheduled-task-log-dialog.html',
                    controller: 'ScheduledTaskLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                className: null,
                                name: null,
                                detailInfo: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scheduled-task-log', null, { reload: 'scheduled-task-log' });
                }, function() {
                    $state.go('scheduled-task-log');
                });
            }]
        })
        .state('scheduled-task-log.edit', {
            parent: 'scheduled-task-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheduled-task-log/scheduled-task-log-dialog.html',
                    controller: 'ScheduledTaskLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ScheduledTaskLog', function(ScheduledTaskLog) {
                            return ScheduledTaskLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scheduled-task-log', null, { reload: 'scheduled-task-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scheduled-task-log.delete', {
            parent: 'scheduled-task-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheduled-task-log/scheduled-task-log-delete-dialog.html',
                    controller: 'ScheduledTaskLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ScheduledTaskLog', function(ScheduledTaskLog) {
                            return ScheduledTaskLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scheduled-task-log', null, { reload: 'scheduled-task-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
