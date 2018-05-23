(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('marketing-new-order-plan', {
            parent: 'entity',
            url: '/marketing-new-order-plan?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.marketingNewOrderPlan.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/marketing-new-order-plan/marketing-new-order-plans.html',
                    controller: 'MarketingNewOrderPlanController',
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
                    $translatePartialLoader.addPart('marketingNewOrderPlan');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('marketing-new-order-plan-detail', {
            parent: 'marketing-new-order-plan',
            url: '/marketing-new-order-plan/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.marketingNewOrderPlan.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/marketing-new-order-plan/marketing-new-order-plan-detail.html',
                    controller: 'MarketingNewOrderPlanDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('marketingNewOrderPlan');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MarketingNewOrderPlan', function($stateParams, MarketingNewOrderPlan) {
                    return MarketingNewOrderPlan.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'marketing-new-order-plan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('marketing-new-order-plan-detail.edit', {
            parent: 'marketing-new-order-plan-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marketing-new-order-plan/marketing-new-order-plan-dialog.html',
                    controller: 'MarketingNewOrderPlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MarketingNewOrderPlan', function(MarketingNewOrderPlan) {
                            return MarketingNewOrderPlan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('marketing-new-order-plan.new', {
            parent: 'marketing-new-order-plan',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marketing-new-order-plan/marketing-new-order-plan-dialog.html',
                    controller: 'MarketingNewOrderPlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                year: null,
                                month: null,
                                targetNumber: null,
                                currentNumber: null,
                                finished: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                percentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('marketing-new-order-plan', null, { reload: 'marketing-new-order-plan' });
                }, function() {
                    $state.go('marketing-new-order-plan');
                });
            }]
        })
        .state('marketing-new-order-plan.edit', {
            parent: 'marketing-new-order-plan',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marketing-new-order-plan/marketing-new-order-plan-dialog.html',
                    controller: 'MarketingNewOrderPlanDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MarketingNewOrderPlan', function(MarketingNewOrderPlan) {
                            return MarketingNewOrderPlan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('marketing-new-order-plan', null, { reload: 'marketing-new-order-plan' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('marketing-new-order-plan.delete', {
            parent: 'marketing-new-order-plan',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marketing-new-order-plan/marketing-new-order-plan-delete-dialog.html',
                    controller: 'MarketingNewOrderPlanDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MarketingNewOrderPlan', function(MarketingNewOrderPlan) {
                            return MarketingNewOrderPlan.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('marketing-new-order-plan', null, { reload: 'marketing-new-order-plan' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
