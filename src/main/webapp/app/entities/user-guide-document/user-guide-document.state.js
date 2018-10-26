(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-guide-document', {
            parent: 'entity',
            url: '/user-guide-document?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.userGuideDocument.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-guide-document/user-guide-documents.html',
                    controller: 'UserGuideDocumentController',
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
                    $translatePartialLoader.addPart('userGuideDocument');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-guide-document-detail', {
            parent: 'user-guide-document',
            url: '/user-guide-document/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.userGuideDocument.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-guide-document/user-guide-document-detail.html',
                    controller: 'UserGuideDocumentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userGuideDocument');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserGuideDocument', function($stateParams, UserGuideDocument) {
                    return UserGuideDocument.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-guide-document',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-guide-document-detail.edit', {
            parent: 'user-guide-document-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-guide-document/user-guide-document-dialog.html',
                    controller: 'UserGuideDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserGuideDocument', function(UserGuideDocument) {
                            return UserGuideDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-guide-document.new', {
            parent: 'user-guide-document',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-guide-document/user-guide-document-dialog.html',
                    controller: 'UserGuideDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                fileName: null,
                                fullUrl: null,
                                fullFolder: "images",
                                baseUrl: "http://www.puzhenchina.com",
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
                    $state.go('user-guide-document', null, { reload: 'user-guide-document' });
                }, function() {
                    $state.go('user-guide-document');
                });
            }]
        })
        .state('user-guide-document.edit', {
            parent: 'user-guide-document',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-guide-document/user-guide-document-dialog.html',
                    controller: 'UserGuideDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserGuideDocument', function(UserGuideDocument) {
                            return UserGuideDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-guide-document', null, { reload: 'user-guide-document' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-guide-document.delete', {
            parent: 'user-guide-document',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-guide-document/user-guide-document-delete-dialog.html',
                    controller: 'UserGuideDocumentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserGuideDocument', function(UserGuideDocument) {
                            return UserGuideDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-guide-document', null, { reload: 'user-guide-document' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
