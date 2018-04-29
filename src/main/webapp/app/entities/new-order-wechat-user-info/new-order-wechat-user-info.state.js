(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('new-order-wechat-user-info', {
            parent: 'entity',
            url: '/new-order-wechat-user-info?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.newOrderWechatUserInfo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/new-order-wechat-user-info/new-order-wechat-user-infos.html',
                    controller: 'NewOrderWechatUserInfoController',
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
                    $translatePartialLoader.addPart('newOrderWechatUserInfo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('new-order-wechat-user-info-detail', {
            parent: 'new-order-wechat-user-info',
            url: '/new-order-wechat-user-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.newOrderWechatUserInfo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/new-order-wechat-user-info/new-order-wechat-user-info-detail.html',
                    controller: 'NewOrderWechatUserInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('newOrderWechatUserInfo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'NewOrderWechatUserInfo', function($stateParams, NewOrderWechatUserInfo) {
                    return NewOrderWechatUserInfo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'new-order-wechat-user-info',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('new-order-wechat-user-info-detail.edit', {
            parent: 'new-order-wechat-user-info-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-wechat-user-info/new-order-wechat-user-info-dialog.html',
                    controller: 'NewOrderWechatUserInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NewOrderWechatUserInfo', function(NewOrderWechatUserInfo) {
                            return NewOrderWechatUserInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('new-order-wechat-user-info.new', {
            parent: 'new-order-wechat-user-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-wechat-user-info/new-order-wechat-user-info-dialog.html',
                    controller: 'NewOrderWechatUserInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                encryptedData: null,
                                iv: null,
                                nickName: null,
                                gender: null,
                                language: null,
                                city: null,
                                province: null,
                                country: null,
                                avatarUrl: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                openId: null,
                                comments: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('new-order-wechat-user-info', null, { reload: 'new-order-wechat-user-info' });
                }, function() {
                    $state.go('new-order-wechat-user-info');
                });
            }]
        })
        .state('new-order-wechat-user-info.edit', {
            parent: 'new-order-wechat-user-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-wechat-user-info/new-order-wechat-user-info-dialog.html',
                    controller: 'NewOrderWechatUserInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NewOrderWechatUserInfo', function(NewOrderWechatUserInfo) {
                            return NewOrderWechatUserInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('new-order-wechat-user-info', null, { reload: 'new-order-wechat-user-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('new-order-wechat-user-info.delete', {
            parent: 'new-order-wechat-user-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/new-order-wechat-user-info/new-order-wechat-user-info-delete-dialog.html',
                    controller: 'NewOrderWechatUserInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NewOrderWechatUserInfo', function(NewOrderWechatUserInfo) {
                            return NewOrderWechatUserInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('new-order-wechat-user-info', null, { reload: 'new-order-wechat-user-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
