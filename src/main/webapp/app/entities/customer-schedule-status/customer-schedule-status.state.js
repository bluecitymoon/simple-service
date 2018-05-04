(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-schedule-status', {
            parent: 'entity',
            url: '/customer-schedule-status?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerScheduleStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-schedule-status/customer-schedule-statuses.html',
                    controller: 'CustomerScheduleStatusController',
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
                    $translatePartialLoader.addPart('customerScheduleStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-schedule-status-detail', {
            parent: 'customer-schedule-status',
            url: '/customer-schedule-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerScheduleStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-schedule-status/customer-schedule-status-detail.html',
                    controller: 'CustomerScheduleStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerScheduleStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerScheduleStatus', function($stateParams, CustomerScheduleStatus) {
                    return CustomerScheduleStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-schedule-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-schedule-status-detail.edit', {
            parent: 'customer-schedule-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-schedule-status/customer-schedule-status-dialog.html',
                    controller: 'CustomerScheduleStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerScheduleStatus', function(CustomerScheduleStatus) {
                            return CustomerScheduleStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-schedule-status.new', {
            parent: 'customer-schedule-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-schedule-status/customer-schedule-status-dialog.html',
                    controller: 'CustomerScheduleStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                code: null,
                                labelStyle: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-schedule-status', null, { reload: 'customer-schedule-status' });
                }, function() {
                    $state.go('customer-schedule-status');
                });
            }]
        })
        .state('customer-schedule-status.edit', {
            parent: 'customer-schedule-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-schedule-status/customer-schedule-status-dialog.html',
                    controller: 'CustomerScheduleStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerScheduleStatus', function(CustomerScheduleStatus) {
                            return CustomerScheduleStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-schedule-status', null, { reload: 'customer-schedule-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-schedule-status.delete', {
            parent: 'customer-schedule-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-schedule-status/customer-schedule-status-delete-dialog.html',
                    controller: 'CustomerScheduleStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerScheduleStatus', function(CustomerScheduleStatus) {
                            return CustomerScheduleStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-schedule-status', null, { reload: 'customer-schedule-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
