(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-cousultant-assign-history', {
            parent: 'entity',
            url: '/customer-cousultant-assign-history?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCousultantAssignHistory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-cousultant-assign-history/customer-cousultant-assign-histories.html',
                    controller: 'CustomerCousultantAssignHistoryController',
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
                    $translatePartialLoader.addPart('customerCousultantAssignHistory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-cousultant-assign-history-detail', {
            parent: 'customer-cousultant-assign-history',
            url: '/customer-cousultant-assign-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCousultantAssignHistory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-cousultant-assign-history/customer-cousultant-assign-history-detail.html',
                    controller: 'CustomerCousultantAssignHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerCousultantAssignHistory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerCousultantAssignHistory', function($stateParams, CustomerCousultantAssignHistory) {
                    return CustomerCousultantAssignHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-cousultant-assign-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-cousultant-assign-history-detail.edit', {
            parent: 'customer-cousultant-assign-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-cousultant-assign-history/customer-cousultant-assign-history-dialog.html',
                    controller: 'CustomerCousultantAssignHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCousultantAssignHistory', function(CustomerCousultantAssignHistory) {
                            return CustomerCousultantAssignHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-cousultant-assign-history.new', {
            parent: 'customer-cousultant-assign-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-cousultant-assign-history/customer-cousultant-assign-history-dialog.html',
                    controller: 'CustomerCousultantAssignHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                olderFollowerLogin: null,
                                olderFollowerName: null,
                                newFollowerLogin: null,
                                newFollowerName: null,
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
                    $state.go('customer-cousultant-assign-history', null, { reload: 'customer-cousultant-assign-history' });
                }, function() {
                    $state.go('customer-cousultant-assign-history');
                });
            }]
        })
        .state('customer-cousultant-assign-history.edit', {
            parent: 'customer-cousultant-assign-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-cousultant-assign-history/customer-cousultant-assign-history-dialog.html',
                    controller: 'CustomerCousultantAssignHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCousultantAssignHistory', function(CustomerCousultantAssignHistory) {
                            return CustomerCousultantAssignHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-cousultant-assign-history', null, { reload: 'customer-cousultant-assign-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-cousultant-assign-history.delete', {
            parent: 'customer-cousultant-assign-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-cousultant-assign-history/customer-cousultant-assign-history-delete-dialog.html',
                    controller: 'CustomerCousultantAssignHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerCousultantAssignHistory', function(CustomerCousultantAssignHistory) {
                            return CustomerCousultantAssignHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-cousultant-assign-history', null, { reload: 'customer-cousultant-assign-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
