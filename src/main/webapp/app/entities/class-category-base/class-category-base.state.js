(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('class-category-base', {
            parent: 'entity',
            url: '/class-category-base?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classCategoryBase.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-category-base/class-category-bases.html',
                    controller: 'ClassCategoryBaseController',
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
                    $translatePartialLoader.addPart('classCategoryBase');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('class-category-base-detail', {
            parent: 'class-category-base',
            url: '/class-category-base/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classCategoryBase.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-category-base/class-category-base-detail.html',
                    controller: 'ClassCategoryBaseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classCategoryBase');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClassCategoryBase', function($stateParams, ClassCategoryBase) {
                    return ClassCategoryBase.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'class-category-base',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('class-category-base-detail.edit', {
            parent: 'class-category-base-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-category-base/class-category-base-dialog.html',
                    controller: 'ClassCategoryBaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassCategoryBase', function(ClassCategoryBase) {
                            return ClassCategoryBase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-category-base.new', {
            parent: 'class-category-base',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-category-base/class-category-base-dialog.html',
                    controller: 'ClassCategoryBaseDialogController',
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
                    $state.go('class-category-base', null, { reload: 'class-category-base' });
                }, function() {
                    $state.go('class-category-base');
                });
            }]
        })
        .state('class-category-base.edit', {
            parent: 'class-category-base',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-category-base/class-category-base-dialog.html',
                    controller: 'ClassCategoryBaseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassCategoryBase', function(ClassCategoryBase) {
                            return ClassCategoryBase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-category-base', null, { reload: 'class-category-base' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-category-base.delete', {
            parent: 'class-category-base',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-category-base/class-category-base-delete-dialog.html',
                    controller: 'ClassCategoryBaseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClassCategoryBase', function(ClassCategoryBase) {
                            return ClassCategoryBase.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-category-base', null, { reload: 'class-category-base' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
