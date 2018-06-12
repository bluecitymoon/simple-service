(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('new-order-resource-location', {
            parent: 'entity',
            url: '/new-order-resource-location?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.newOrderResourceLocation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/new-order-resource-location/new-order-resource-locations.html',
                    controller: 'NewOrderResourceLocationController',
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
                    $translatePartialLoader.addPart('newOrderResourceLocation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('new-order-resource-location-detail', {
            parent: 'new-order-resource-location',
            url: '/new-order-resource-location/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.newOrderResourceLocation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/new-order-resource-location/new-order-resource-location-detail.html',
                    controller: 'NewOrderResourceLocationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('newOrderResourceLocation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NewOrderResourceLocation', function($stateParams, NewOrderResourceLocation) {
                    return NewOrderResourceLocation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'new-order-resource-location',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('new-order-resource-location-detail.edit', {
            parent: 'new-order-resource-location-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-resource-location/new-order-resource-location-dialog.html',
                    controller: 'NewOrderResourceLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NewOrderResourceLocation', function(NewOrderResourceLocation) {
                            return NewOrderResourceLocation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('new-order-resource-location.new', {
            parent: 'new-order-resource-location',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-resource-location/new-order-resource-location-dialog.html',
                    controller: 'NewOrderResourceLocationDialogController',
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
                    $state.go('new-order-resource-location', null, { reload: 'new-order-resource-location' });
                }, function() {
                    $state.go('new-order-resource-location');
                });
            }]
        })
        .state('new-order-resource-location.edit', {
            parent: 'new-order-resource-location',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-resource-location/new-order-resource-location-dialog.html',
                    controller: 'NewOrderResourceLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NewOrderResourceLocation', function(NewOrderResourceLocation) {
                            return NewOrderResourceLocation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('new-order-resource-location', null, { reload: 'new-order-resource-location' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('new-order-resource-location.delete', {
            parent: 'new-order-resource-location',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-resource-location/new-order-resource-location-delete-dialog.html',
                    controller: 'NewOrderResourceLocationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NewOrderResourceLocation', function(NewOrderResourceLocation) {
                            return NewOrderResourceLocation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('new-order-resource-location', null, { reload: 'new-order-resource-location' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
