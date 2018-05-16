(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('marketing-qrcode', {
            parent: 'entity',
            url: '/marketing-qrcode?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.marketingQrcode.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/marketing-qrcode/marketing-qrcodes.html',
                    controller: 'MarketingQrcodeController',
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
                    $translatePartialLoader.addPart('marketingQrcode');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('marketing-qrcode-detail', {
            parent: 'marketing-qrcode',
            url: '/marketing-qrcode/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.marketingQrcode.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/marketing-qrcode/marketing-qrcode-detail.html',
                    controller: 'MarketingQrcodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('marketingQrcode');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MarketingQrcode', function($stateParams, MarketingQrcode) {
                    return MarketingQrcode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'marketing-qrcode',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('marketing-qrcode-detail.edit', {
            parent: 'marketing-qrcode-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marketing-qrcode/marketing-qrcode-dialog.html',
                    controller: 'MarketingQrcodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MarketingQrcode', function(MarketingQrcode) {
                            return MarketingQrcode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('marketing-qrcode.new', {
            parent: 'marketing-qrcode',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marketing-qrcode/marketing-qrcode-dialog.html',
                    controller: 'MarketingQrcodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fileUrl: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('marketing-qrcode', null, { reload: 'marketing-qrcode' });
                }, function() {
                    $state.go('marketing-qrcode');
                });
            }]
        })
        .state('marketing-qrcode.edit', {
            parent: 'marketing-qrcode',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marketing-qrcode/marketing-qrcode-dialog.html',
                    controller: 'MarketingQrcodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MarketingQrcode', function(MarketingQrcode) {
                            return MarketingQrcode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('marketing-qrcode', null, { reload: 'marketing-qrcode' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('marketing-qrcode.delete', {
            parent: 'marketing-qrcode',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/marketing-qrcode/marketing-qrcode-delete-dialog.html',
                    controller: 'MarketingQrcodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MarketingQrcode', function(MarketingQrcode) {
                            return MarketingQrcode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('marketing-qrcode', null, { reload: 'marketing-qrcode' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
