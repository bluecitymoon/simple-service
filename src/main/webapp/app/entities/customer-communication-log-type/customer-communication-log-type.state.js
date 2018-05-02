(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-communication-log-type', {
            parent: 'entity',
            url: '/customer-communication-log-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCommunicationLogType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-communication-log-type/customer-communication-log-types.html',
                    controller: 'CustomerCommunicationLogTypeController',
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
                    $translatePartialLoader.addPart('customerCommunicationLogType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-communication-log-type-detail', {
            parent: 'customer-communication-log-type',
            url: '/customer-communication-log-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCommunicationLogType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-communication-log-type/customer-communication-log-type-detail.html',
                    controller: 'CustomerCommunicationLogTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerCommunicationLogType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerCommunicationLogType', function($stateParams, CustomerCommunicationLogType) {
                    return CustomerCommunicationLogType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-communication-log-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-communication-log-type-detail.edit', {
            parent: 'customer-communication-log-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-log-type/customer-communication-log-type-dialog.html',
                    controller: 'CustomerCommunicationLogTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCommunicationLogType', function(CustomerCommunicationLogType) {
                            return CustomerCommunicationLogType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-communication-log-type.new', {
            parent: 'customer-communication-log-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-log-type/customer-communication-log-type-dialog.html',
                    controller: 'CustomerCommunicationLogTypeDialogController',
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
                    $state.go('customer-communication-log-type', null, { reload: 'customer-communication-log-type' });
                }, function() {
                    $state.go('customer-communication-log-type');
                });
            }]
        })
        .state('customer-communication-log-type.edit', {
            parent: 'customer-communication-log-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-log-type/customer-communication-log-type-dialog.html',
                    controller: 'CustomerCommunicationLogTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCommunicationLogType', function(CustomerCommunicationLogType) {
                            return CustomerCommunicationLogType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-communication-log-type', null, { reload: 'customer-communication-log-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-communication-log-type.delete', {
            parent: 'customer-communication-log-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-log-type/customer-communication-log-type-delete-dialog.html',
                    controller: 'CustomerCommunicationLogTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerCommunicationLogType', function(CustomerCommunicationLogType) {
                            return CustomerCommunicationLogType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-communication-log-type', null, { reload: 'customer-communication-log-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
