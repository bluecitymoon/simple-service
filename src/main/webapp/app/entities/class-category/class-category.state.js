(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('class-category', {
            parent: 'entity',
            url: '/class-category?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-category/class-categories.html',
                    controller: 'ClassCategoryController',
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
                    $translatePartialLoader.addPart('classCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('class-category-detail', {
            parent: 'class-category',
            url: '/class-category/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.classCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-category/class-category-detail.html',
                    controller: 'ClassCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClassCategory', function($stateParams, ClassCategory) {
                    return ClassCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'class-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('class-category-detail.edit', {
            parent: 'class-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-category/class-category-dialog.html',
                    controller: 'ClassCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassCategory', function(ClassCategory) {
                            return ClassCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-category.new', {
            parent: 'class-category',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-category/class-category-dialog.html',
                    controller: 'ClassCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                code: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('class-category', null, { reload: 'class-category' });
                }, function() {
                    $state.go('class-category');
                });
            }]
        })
        .state('class-category.edit', {
            parent: 'class-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-category/class-category-dialog.html',
                    controller: 'ClassCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassCategory', function(ClassCategory) {
                            return ClassCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-category', null, { reload: 'class-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-category.delete', {
            parent: 'class-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-category/class-category-delete-dialog.html',
                    controller: 'ClassCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClassCategory', function(ClassCategory) {
                            return ClassCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-category', null, { reload: 'class-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
