(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-communication-schedule', {
            parent: 'entity',
            url: '/customer-communication-schedule?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCommunicationSchedule.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-communication-schedule/customer-communication-schedules.html',
                    controller: 'CustomerCommunicationScheduleController',
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
                    $translatePartialLoader.addPart('customerCommunicationSchedule');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-communication-schedule-detail', {
            parent: 'customer-communication-schedule',
            url: '/customer-communication-schedule/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCommunicationSchedule.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-communication-schedule/customer-communication-schedule-detail.html',
                    controller: 'CustomerCommunicationScheduleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerCommunicationSchedule');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerCommunicationSchedule', function($stateParams, CustomerCommunicationSchedule) {
                    return CustomerCommunicationSchedule.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-communication-schedule',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-communication-schedule-detail.edit', {
            parent: 'customer-communication-schedule-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-schedule/customer-communication-schedule-dialog.html',
                    controller: 'CustomerCommunicationScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCommunicationSchedule', function(CustomerCommunicationSchedule) {
                            return CustomerCommunicationSchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-communication-schedule.new', {
            parent: 'customer-communication-schedule',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-schedule/customer-communication-schedule-dialog.html',
                    controller: 'CustomerCommunicationScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sceduleDate: null,
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
                    $state.go('customer-communication-schedule', null, { reload: 'customer-communication-schedule' });
                }, function() {
                    $state.go('customer-communication-schedule');
                });
            }]
        })
        .state('customer-communication-schedule.edit', {
            parent: 'customer-communication-schedule',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-schedule/customer-communication-schedule-dialog.html',
                    controller: 'CustomerCommunicationScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCommunicationSchedule', function(CustomerCommunicationSchedule) {
                            return CustomerCommunicationSchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-communication-schedule', null, { reload: 'customer-communication-schedule' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-communication-schedule.delete', {
            parent: 'customer-communication-schedule',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-communication-schedule/customer-communication-schedule-delete-dialog.html',
                    controller: 'CustomerCommunicationScheduleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerCommunicationSchedule', function(CustomerCommunicationSchedule) {
                            return CustomerCommunicationSchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-communication-schedule', null, { reload: 'customer-communication-schedule' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
