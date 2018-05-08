(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('class-arrangement-rule', {
            parent: 'entity',
            url: '/class-arrangement-rule?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classArrangementRule.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-arrangement-rule/class-arrangement-rules.html',
                    controller: 'ClassArrangementRuleController',
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
                    $translatePartialLoader.addPart('classArrangementRule');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('class-arrangement-rule-detail', {
            parent: 'class-arrangement-rule',
            url: '/class-arrangement-rule/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classArrangementRule.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-arrangement-rule/class-arrangement-rule-detail.html',
                    controller: 'ClassArrangementRuleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classArrangementRule');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClassArrangementRule', function($stateParams, ClassArrangementRule) {
                    return ClassArrangementRule.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'class-arrangement-rule',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('class-arrangement-rule-detail.edit', {
            parent: 'class-arrangement-rule-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-rule/class-arrangement-rule-dialog.html',
                    controller: 'ClassArrangementRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassArrangementRule', function(ClassArrangementRule) {
                            return ClassArrangementRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                     $state.go('^', {}, { reload: false });
                }, function() {
                     $state.go('^');
                });
            }]
        })
        .state('class-arrangement-rule.new', {
            parent: 'class-arrangement-rule',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-rule/class-arrangement-rule-dialog.html',
                    controller: 'ClassArrangementRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                estimateStartDate: null,
                                estimateStartTime: null,
                                estimateEndTime: null,
                                maxLoopCount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('class-arrangement-rule', null, { reload: 'class-arrangement-rule' });
                }, function() {
                    $state.go('class-arrangement-rule');
                });
            }]
        })
        .state('class-arrangement-rule.edit', {
            parent: 'class-arrangement-rule',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-rule/class-arrangement-rule-dialog.html',
                    controller: 'ClassArrangementRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassArrangementRule', function(ClassArrangementRule) {
                            return ClassArrangementRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-arrangement-rule', null, { reload: 'class-arrangement-rule' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-arrangement-rule.delete', {
            parent: 'class-arrangement-rule',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-rule/class-arrangement-rule-delete-dialog.html',
                    controller: 'ClassArrangementRuleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClassArrangementRule', function(ClassArrangementRule) {
                            return ClassArrangementRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-arrangement-rule', null, { reload: 'class-arrangement-rule' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
