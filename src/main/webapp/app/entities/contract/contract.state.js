(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contract', {
            parent: 'entity',
            url: '/contract?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.contract.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contract/contracts.html',
                    controller: 'ContractController',
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
                    $translatePartialLoader.addPart('contract');
                    $translatePartialLoader.addPart('student');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('contract-detail', {
            parent: 'contract',
            url: '/contract/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.contract.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contract/contract-detail.html',
                    controller: 'ContractDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contract');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Contract', function($stateParams, Contract) {
                    return Contract.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'contract',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('contract-detail.edit', {
            parent: 'contract-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contract/contract-dialog.html',
                    controller: 'ContractDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contract', function(Contract) {
                            return Contract.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contract.new', {
            parent: 'contract',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contract/contract-dialog.html',
                    controller: 'ContractDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contractNumber: null,
                                serialNumber: null,
                                signDate: null,
                                startDate: null,
                                endDate: null,
                                totalMoneyAmount: null,
                                moneyShouldCollected: null,
                                moneyCollected: null,
                                promotionAmount: null,
                                totalHours: null,
                                hoursTaken: null,
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
                    $state.go('contract', null, { reload: 'contract' });
                }, function() {
                    $state.go('contract');
                });
            }]
        })
            .state('contract.new-packaged-contracts', {
                parent: 'contract',
                url: '/new-packaged-contracts',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/contract/contract-dialog-package.html',
                        controller: 'ContractDialogPackageController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    contractNumber: null,
                                    serialNumber: null,
                                    signDate: null,
                                    startDate: null,
                                    endDate: null,
                                    totalMoneyAmount: null,
                                    moneyShouldCollected: null,
                                    moneyCollected: null,
                                    promotionAmount: null,
                                    totalHours: null,
                                    hoursTaken: null,
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
                        $state.go('contract', null, { reload: 'contract' });
                    }, function() {
                        $state.go('contract');
                    });
                }]
            })
        .state('contract.edit', {
            parent: 'contract',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contract/contract-dialog.html',
                    controller: 'ContractDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contract', function(Contract) {
                            return Contract.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contract', null, { reload: 'contract' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contract.delete', {
            parent: 'contract',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contract/contract-delete-dialog.html',
                    controller: 'ContractDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Contract', function(Contract) {
                            return Contract.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contract', null, { reload: 'contract' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
