(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-consumer-log', {
            parent: 'entity',
            url: '/customer-consumer-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerConsumerLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-consumer-log/customer-consumer-logs.html',
                    controller: 'CustomerConsumerLogController',
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
                    $translatePartialLoader.addPart('customerConsumerLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-consumer-log-detail', {
            parent: 'customer-consumer-log',
            url: '/customer-consumer-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerConsumerLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-consumer-log/customer-consumer-log-detail.html',
                    controller: 'CustomerConsumerLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerConsumerLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerConsumerLog', function($stateParams, CustomerConsumerLog) {
                    return CustomerConsumerLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-consumer-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-consumer-log-detail.edit', {
            parent: 'customer-consumer-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-consumer-log/customer-consumer-log-dialog.html',
                    controller: 'CustomerConsumerLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerConsumerLog', function(CustomerConsumerLog) {
                            return CustomerConsumerLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-consumer-log.new', {
            parent: 'customer-consumer-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-consumer-log/customer-consumer-log-dialog.html',
                    controller: 'CustomerConsumerLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                count: null,
                                unit: null,
                                uniqueNumber: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                consumerName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-consumer-log', null, { reload: 'customer-consumer-log' });
                }, function() {
                    $state.go('customer-consumer-log');
                });
            }]
        })
        .state('customer-consumer-log.edit', {
            parent: 'customer-consumer-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-consumer-log/customer-consumer-log-dialog.html',
                    controller: 'CustomerConsumerLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerConsumerLog', function(CustomerConsumerLog) {
                            return CustomerConsumerLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-consumer-log', null, { reload: 'customer-consumer-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-consumer-log.delete', {
            parent: 'customer-consumer-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-consumer-log/customer-consumer-log-delete-dialog.html',
                    controller: 'CustomerConsumerLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerConsumerLog', function(CustomerConsumerLog) {
                            return CustomerConsumerLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-consumer-log', null, { reload: 'customer-consumer-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
