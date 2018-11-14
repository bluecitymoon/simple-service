(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('class-arrangement', {
            parent: 'entity',
            url: '/class-arrangement?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classArrangement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-arrangement/class-arrangements.html',
                    controller: 'ClassArrangementController',
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
                    $translatePartialLoader.addPart('classArrangement');
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('product');
                    $translatePartialLoader.addPart('studentClassLog');
                    return $translate.refresh();
                }]
            }
        })
        .state('class-arrangement-detail', {
            parent: 'class-arrangement',
            url: '/class-arrangement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classArrangement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-arrangement/class-arrangement-detail.html',
                    controller: 'ClassArrangementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classArrangement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClassArrangement', function($stateParams, ClassArrangement) {
                    return ClassArrangement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'class-arrangement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('class-arrangement-detail.edit', {
            parent: 'class-arrangement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement/class-arrangement-dialog.html',
                    controller: 'ClassArrangementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassArrangement', function(ClassArrangement) {
                            return ClassArrangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-arrangement.new', {
            parent: 'class-arrangement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement/class-arrangement-dialog.html',
                    controller: 'ClassArrangementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('class-arrangement', null, { reload: 'class-arrangement' });
                }, function() {
                    $state.go('class-arrangement');
                });
            }]
        })
        .state('class-arrangement.edit', {
            parent: 'class-arrangement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement/class-arrangement-dialog.html',
                    controller: 'ClassArrangementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassArrangement', function(ClassArrangement) {
                            return ClassArrangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-arrangement', null, { reload: 'class-arrangement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-arrangement.delete', {
            parent: 'class-arrangement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-arrangement/class-arrangement-delete-dialog.html',
                    controller: 'ClassArrangementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClassArrangement', function(ClassArrangement) {
                            return ClassArrangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-arrangement', null, { reload: 'class-arrangement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
