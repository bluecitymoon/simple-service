(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contract-package', {
            parent: 'entity',
            url: '/contract-package?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.contractPackage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contract-package/contract-packages.html',
                    controller: 'ContractPackageController',
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
                    $translatePartialLoader.addPart('contractPackage');
                    $translatePartialLoader.addPart('contractTemplate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('contract-package-detail', {
            parent: 'contract-package',
            url: '/contract-package/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.contractPackage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contract-package/contract-package-detail.html',
                    controller: 'ContractPackageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contractPackage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ContractPackage', function($stateParams, ContractPackage) {
                    return ContractPackage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'contract-package',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('contract-package-detail.edit', {
            parent: 'contract-package-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contract-package/contract-package-dialog.html',
                    controller: 'ContractPackageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContractPackage', function(ContractPackage) {
                            return ContractPackage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contract-package.new', {
            parent: 'contract-package',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contract-package/contract-package-dialog.html',
                    controller: 'ContractPackageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                code: null,
                                comments: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('contract-package', null, { reload: 'contract-package' });
                }, function() {
                    $state.go('contract-package');
                });
            }]
        })
        .state('contract-package.edit', {
            parent: 'contract-package',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contract-package/contract-package-dialog.html',
                    controller: 'ContractPackageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContractPackage', function(ContractPackage) {
                            return ContractPackage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contract-package', null, { reload: 'contract-package' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contract-package.delete', {
            parent: 'contract-package',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contract-package/contract-package-delete-dialog.html',
                    controller: 'ContractPackageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ContractPackage', function(ContractPackage) {
                            return ContractPackage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contract-package', null, { reload: 'contract-package' });
                }, function() {
                    $state.go('^');
                });
            }]
        })

            .state('contract-package.new-template', {
                parent: 'contract-package-detail',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/contract-template/contract-template-dialog.html',
                        controller: 'ContractTemplateDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    totalMoneyAmount: null,
                                    classCount: null,
                                    totalMinutes: null,
                                    totalHours: null,
                                    years: null,
                                    promotionAmount: null,
                                    createdBy: null,
                                    createdDate: null,
                                    lastModifiedBy: null,
                                    lastModifiedDate: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('^');
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('contract-package.edit-template', {
                parent: 'contract-package-detail',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/contract-template/contract-template-dialog.html',
                        controller: 'ContractTemplateDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['ContractTemplate', function(ContractTemplate) {
                                return ContractTemplate.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('^');

                    }, function() {
                        $state.go('^');

                    });
                }]
            })
            .state('contract-package-template.delete', {
                parent: 'contract-package-detail',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/contract-template/contract-template-delete-dialog.html',
                        controller: 'ContractTemplateDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['ContractTemplate', function(ContractTemplate) {
                                return ContractTemplate.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {

                    }, function() {

                    });
                }]
            });
    }

})();
