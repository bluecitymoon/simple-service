(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ad', {
            parent: 'entity',
            url: '/ad?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.ad.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ad/ads.html',
                    controller: 'AdController',
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
                    $translatePartialLoader.addPart('ad');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ad-detail', {
            parent: 'ad',
            url: '/ad/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.ad.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ad/ad-detail.html',
                    controller: 'AdDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ad');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ad', function($stateParams, Ad) {
                    return Ad.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ad',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ad-detail.edit', {
            parent: 'ad-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ad/ad-dialog.html',
                    controller: 'AdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ad', function(Ad) {
                            return Ad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ad.new', {
            parent: 'ad',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ad/ad-dialog.html',
                    controller: 'AdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                content: null,
                                rpxWidth: null,
                                rpxHeight: null,
                                status: null,
                                sequence: null,
                                comments: null,
                                createdBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ad', null, { reload: 'ad' });
                }, function() {
                    $state.go('ad');
                });
            }]
        })
        .state('ad.edit', {
            parent: 'ad',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ad/ad-dialog.html',
                    controller: 'AdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ad', function(Ad) {
                            return Ad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ad', null, { reload: 'ad' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ad.delete', {
            parent: 'ad',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ad/ad-delete-dialog.html',
                    controller: 'AdDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ad', function(Ad) {
                            return Ad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ad', null, { reload: 'ad' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
