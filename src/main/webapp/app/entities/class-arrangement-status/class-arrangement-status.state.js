(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('class-arrangement-status', {
            parent: 'entity',
            url: '/class-arrangement-status?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classArrangementStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-arrangement-status/class-arrangement-statuses.html',
                    controller: 'ClassArrangementStatusController',
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
                    $translatePartialLoader.addPart('classArrangementStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('class-arrangement-status-detail', {
            parent: 'class-arrangement-status',
            url: '/class-arrangement-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classArrangementStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-arrangement-status/class-arrangement-status-detail.html',
                    controller: 'ClassArrangementStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classArrangementStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClassArrangementStatus', function($stateParams, ClassArrangementStatus) {
                    return ClassArrangementStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'class-arrangement-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('class-arrangement-status-detail.edit', {
            parent: 'class-arrangement-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-status/class-arrangement-status-dialog.html',
                    controller: 'ClassArrangementStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassArrangementStatus', function(ClassArrangementStatus) {
                            return ClassArrangementStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-arrangement-status.new', {
            parent: 'class-arrangement-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-status/class-arrangement-status-dialog.html',
                    controller: 'ClassArrangementStatusDialogController',
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
                    $state.go('class-arrangement-status', null, { reload: 'class-arrangement-status' });
                }, function() {
                    $state.go('class-arrangement-status');
                });
            }]
        })
        .state('class-arrangement-status.edit', {
            parent: 'class-arrangement-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-status/class-arrangement-status-dialog.html',
                    controller: 'ClassArrangementStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassArrangementStatus', function(ClassArrangementStatus) {
                            return ClassArrangementStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-arrangement-status', null, { reload: 'class-arrangement-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-arrangement-status.delete', {
            parent: 'class-arrangement-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-status/class-arrangement-status-delete-dialog.html',
                    controller: 'ClassArrangementStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClassArrangementStatus', function(ClassArrangementStatus) {
                            return ClassArrangementStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-arrangement-status', null, { reload: 'class-arrangement-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
