(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('system-variable', {
            parent: 'entity',
            url: '/system-variable?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.systemVariable.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/system-variable/system-variables.html',
                    controller: 'SystemVariableController',
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
                    $translatePartialLoader.addPart('systemVariable');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('system-variable-detail', {
            parent: 'system-variable',
            url: '/system-variable/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.systemVariable.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/system-variable/system-variable-detail.html',
                    controller: 'SystemVariableDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('systemVariable');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SystemVariable', function($stateParams, SystemVariable) {
                    return SystemVariable.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'system-variable',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('system-variable-detail.edit', {
            parent: 'system-variable-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system-variable/system-variable-dialog.html',
                    controller: 'SystemVariableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SystemVariable', function(SystemVariable) {
                            return SystemVariable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('system-variable.new', {
            parent: 'system-variable',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system-variable/system-variable-dialog.html',
                    controller: 'SystemVariableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                attrValue: null,
                                comments: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('system-variable', null, { reload: 'system-variable' });
                }, function() {
                    $state.go('system-variable');
                });
            }]
        })
        .state('system-variable.edit', {
            parent: 'system-variable',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system-variable/system-variable-dialog.html',
                    controller: 'SystemVariableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SystemVariable', function(SystemVariable) {
                            return SystemVariable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('system-variable', null, { reload: 'system-variable' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('system-variable.delete', {
            parent: 'system-variable',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system-variable/system-variable-delete-dialog.html',
                    controller: 'SystemVariableDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SystemVariable', function(SystemVariable) {
                            return SystemVariable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('system-variable', null, { reload: 'system-variable' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
