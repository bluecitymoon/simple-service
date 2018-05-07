(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('class-arrangement-rule-loop-way', {
            parent: 'entity',
            url: '/class-arrangement-rule-loop-way?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classArrangementRuleLoopWay.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-arrangement-rule-loop-way/class-arrangement-rule-loop-ways.html',
                    controller: 'ClassArrangementRuleLoopWayController',
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
                    $translatePartialLoader.addPart('classArrangementRuleLoopWay');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('class-arrangement-rule-loop-way-detail', {
            parent: 'class-arrangement-rule-loop-way',
            url: '/class-arrangement-rule-loop-way/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classArrangementRuleLoopWay.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-arrangement-rule-loop-way/class-arrangement-rule-loop-way-detail.html',
                    controller: 'ClassArrangementRuleLoopWayDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classArrangementRuleLoopWay');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClassArrangementRuleLoopWay', function($stateParams, ClassArrangementRuleLoopWay) {
                    return ClassArrangementRuleLoopWay.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'class-arrangement-rule-loop-way',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('class-arrangement-rule-loop-way-detail.edit', {
            parent: 'class-arrangement-rule-loop-way-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-rule-loop-way/class-arrangement-rule-loop-way-dialog.html',
                    controller: 'ClassArrangementRuleLoopWayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassArrangementRuleLoopWay', function(ClassArrangementRuleLoopWay) {
                            return ClassArrangementRuleLoopWay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-arrangement-rule-loop-way.new', {
            parent: 'class-arrangement-rule-loop-way',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-rule-loop-way/class-arrangement-rule-loop-way-dialog.html',
                    controller: 'ClassArrangementRuleLoopWayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('class-arrangement-rule-loop-way', null, { reload: 'class-arrangement-rule-loop-way' });
                }, function() {
                    $state.go('class-arrangement-rule-loop-way');
                });
            }]
        })
        .state('class-arrangement-rule-loop-way.edit', {
            parent: 'class-arrangement-rule-loop-way',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-rule-loop-way/class-arrangement-rule-loop-way-dialog.html',
                    controller: 'ClassArrangementRuleLoopWayDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassArrangementRuleLoopWay', function(ClassArrangementRuleLoopWay) {
                            return ClassArrangementRuleLoopWay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-arrangement-rule-loop-way', null, { reload: 'class-arrangement-rule-loop-way' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-arrangement-rule-loop-way.delete', {
            parent: 'class-arrangement-rule-loop-way',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement-rule-loop-way/class-arrangement-rule-loop-way-delete-dialog.html',
                    controller: 'ClassArrangementRuleLoopWayDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClassArrangementRuleLoopWay', function(ClassArrangementRuleLoopWay) {
                            return ClassArrangementRuleLoopWay.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-arrangement-rule-loop-way', null, { reload: 'class-arrangement-rule-loop-way' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
