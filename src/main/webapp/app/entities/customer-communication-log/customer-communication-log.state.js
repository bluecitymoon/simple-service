(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-communication-log', {
            parent: 'entity',
            url: '/customer-communication-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCommunicationLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-communication-log/customer-communication-logs.html',
                    controller: 'CustomerCommunicationLogController',
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
                    $translatePartialLoader.addPart('customerCommunicationLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-communication-log-detail', {
            parent: 'customer-communication-log',
            url: '/customer-communication-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCommunicationLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-communication-log/customer-communication-log-detail.html',
                    controller: 'CustomerCommunicationLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerCommunicationLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerCommunicationLog', function($stateParams, CustomerCommunicationLog) {
                    return CustomerCommunicationLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-communication-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-communication-log-detail.edit', {
            parent: 'customer-communication-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-log/customer-communication-log-dialog.html',
                    controller: 'CustomerCommunicationLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCommunicationLog', function(CustomerCommunicationLog) {
                            return CustomerCommunicationLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-communication-log.new', {
            parent: 'customer-communication-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-log/customer-communication-log-dialog.html',
                    controller: 'CustomerCommunicationLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                comments: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-communication-log', null, { reload: 'customer-communication-log' });
                }, function() {
                    $state.go('customer-communication-log');
                });
            }]
        })
        .state('customer-communication-log.edit', {
            parent: 'customer-communication-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-log/customer-communication-log-dialog.html',
                    controller: 'CustomerCommunicationLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCommunicationLog', function(CustomerCommunicationLog) {
                            return CustomerCommunicationLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-communication-log', null, { reload: 'customer-communication-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-communication-log.delete', {
            parent: 'customer-communication-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-log/customer-communication-log-delete-dialog.html',
                    controller: 'CustomerCommunicationLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerCommunicationLog', function(CustomerCommunicationLog) {
                            return CustomerCommunicationLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-communication-log', null, { reload: 'customer-communication-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
