(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-card', {
            parent: 'entity',
            url: '/customer-card?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCard.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-card/customer-cards.html',
                    controller: 'CustomerCardController',
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
                    $translatePartialLoader.addPart('customerCard');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-card-detail', {
            parent: 'customer-card',
            url: '/customer-card/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCard.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-card/customer-card-detail.html',
                    controller: 'CustomerCardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerCard');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerCard', function($stateParams, CustomerCard) {
                    return CustomerCard.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-card',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-card-detail.edit', {
            parent: 'customer-card-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card/customer-card-dialog.html',
                    controller: 'CustomerCardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCard', function(CustomerCard) {
                            return CustomerCard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-card.new', {
            parent: 'customer-card',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card/customer-card-dialog.html',
                    controller: 'CustomerCardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                number: null,
                                serialNumber: null,
                                signDate: null,
                                startDate: null,
                                endDate: null,
                                moneyCollected: null,
                                balance: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                totalMoneyAmount: null,
                                promotionAmount: null,
                                classCount: null,
                                totalMinutes: null,
                                specialPromotionAmount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-card', null, { reload: 'customer-card' });
                }, function() {
                    $state.go('customer-card');
                });
            }]
        })
        .state('customer-card.edit', {
            parent: 'customer-card',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card/customer-card-dialog.html',
                    controller: 'CustomerCardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCard', function(CustomerCard) {
                            return CustomerCard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-card', null, { reload: 'customer-card' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-card.delete', {
            parent: 'customer-card',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card/customer-card-delete-dialog.html',
                    controller: 'CustomerCardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerCard', function(CustomerCard) {
                            return CustomerCard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-card', null, { reload: 'customer-card' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
