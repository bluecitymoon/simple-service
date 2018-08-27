(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-class-plan', {
            parent: 'entity',
            url: '/free-class-plan?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.freeClassPlan.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-class-plan/free-class-plans.html',
                    controller: 'FreeClassPlanController',
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
                    $translatePartialLoader.addPart('freeClassPlan');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-class-plan-detail', {
            parent: 'free-class-plan',
            url: '/free-class-plan/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.freeClassPlan.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-class-plan/free-class-plan-detail.html',
                    controller: 'FreeClassPlanDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freeClassPlan');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FreeClassPlan', function($stateParams, FreeClassPlan) {
                    return FreeClassPlan.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'free-class-plan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('free-class-plan-detail.edit', {
            parent: 'free-class-plan-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-class-plan/free-class-plan-dialog.html',
                    controller: 'FreeClassPlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FreeClassPlan', function(FreeClassPlan) {
                            return FreeClassPlan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-class-plan.new', {
            parent: 'free-class-plan',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-class-plan/free-class-plan-dialog.html',
                    controller: 'FreeClassPlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                planDate: null,
                                limitCount: null,
                                actualCount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-class-plan', null, { reload: 'free-class-plan' });
                }, function() {
                    $state.go('free-class-plan');
                });
            }]
        })
        .state('free-class-plan.edit', {
            parent: 'free-class-plan',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-class-plan/free-class-plan-dialog.html',
                    controller: 'FreeClassPlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FreeClassPlan', function(FreeClassPlan) {
                            return FreeClassPlan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-class-plan', null, { reload: 'free-class-plan' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-class-plan.delete', {
            parent: 'free-class-plan',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-class-plan/free-class-plan-delete-dialog.html',
                    controller: 'FreeClassPlanDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FreeClassPlan', function(FreeClassPlan) {
                            return FreeClassPlan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-class-plan', null, { reload: 'free-class-plan' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
