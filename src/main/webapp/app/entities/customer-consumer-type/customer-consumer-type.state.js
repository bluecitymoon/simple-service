(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-consumer-type', {
            parent: 'entity',
            url: '/customer-consumer-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerConsumerType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-consumer-type/customer-consumer-types.html',
                    controller: 'CustomerConsumerTypeController',
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
                    $translatePartialLoader.addPart('customerConsumerType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-consumer-type-detail', {
            parent: 'customer-consumer-type',
            url: '/customer-consumer-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerConsumerType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-consumer-type/customer-consumer-type-detail.html',
                    controller: 'CustomerConsumerTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerConsumerType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerConsumerType', function($stateParams, CustomerConsumerType) {
                    return CustomerConsumerType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-consumer-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-consumer-type-detail.edit', {
            parent: 'customer-consumer-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-consumer-type/customer-consumer-type-dialog.html',
                    controller: 'CustomerConsumerTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerConsumerType', function(CustomerConsumerType) {
                            return CustomerConsumerType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-consumer-type.new', {
            parent: 'customer-consumer-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-consumer-type/customer-consumer-type-dialog.html',
                    controller: 'CustomerConsumerTypeDialogController',
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
                    $state.go('customer-consumer-type', null, { reload: 'customer-consumer-type' });
                }, function() {
                    $state.go('customer-consumer-type');
                });
            }]
        })
        .state('customer-consumer-type.edit', {
            parent: 'customer-consumer-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-consumer-type/customer-consumer-type-dialog.html',
                    controller: 'CustomerConsumerTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerConsumerType', function(CustomerConsumerType) {
                            return CustomerConsumerType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-consumer-type', null, { reload: 'customer-consumer-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-consumer-type.delete', {
            parent: 'customer-consumer-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-consumer-type/customer-consumer-type-delete-dialog.html',
                    controller: 'CustomerConsumerTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerConsumerType', function(CustomerConsumerType) {
                            return CustomerConsumerType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-consumer-type', null, { reload: 'customer-consumer-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
