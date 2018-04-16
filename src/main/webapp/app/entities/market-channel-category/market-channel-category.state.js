(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('market-channel-category', {
            parent: 'entity',
            url: '/market-channel-category?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.marketChannelCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/market-channel-category/market-channel-categories.html',
                    controller: 'MarketChannelCategoryController',
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
                    $translatePartialLoader.addPart('marketChannelCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('market-channel-category-detail', {
            parent: 'market-channel-category',
            url: '/market-channel-category/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.marketChannelCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/market-channel-category/market-channel-category-detail.html',
                    controller: 'MarketChannelCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('marketChannelCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MarketChannelCategory', function($stateParams, MarketChannelCategory) {
                    return MarketChannelCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'market-channel-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('market-channel-category-detail.edit', {
            parent: 'market-channel-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market-channel-category/market-channel-category-dialog.html',
                    controller: 'MarketChannelCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MarketChannelCategory', function(MarketChannelCategory) {
                            return MarketChannelCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('market-channel-category.new', {
            parent: 'market-channel-category',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market-channel-category/market-channel-category-dialog.html',
                    controller: 'MarketChannelCategoryDialogController',
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
                    $state.go('market-channel-category', null, { reload: 'market-channel-category' });
                }, function() {
                    $state.go('market-channel-category');
                });
            }]
        })
        .state('market-channel-category.edit', {
            parent: 'market-channel-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market-channel-category/market-channel-category-dialog.html',
                    controller: 'MarketChannelCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MarketChannelCategory', function(MarketChannelCategory) {
                            return MarketChannelCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('market-channel-category', null, { reload: 'market-channel-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('market-channel-category.delete', {
            parent: 'market-channel-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/market-channel-category/market-channel-category-delete-dialog.html',
                    controller: 'MarketChannelCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MarketChannelCategory', function(MarketChannelCategory) {
                            return MarketChannelCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('market-channel-category', null, { reload: 'market-channel-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
