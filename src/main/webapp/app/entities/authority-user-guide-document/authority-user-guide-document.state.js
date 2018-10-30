(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('authority-user-guide-document', {
            parent: 'entity',
            url: '/authority-user-guide-document?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.authorityUserGuideDocument.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/authority-user-guide-document/authority-user-guide-documents.html',
                    controller: 'AuthorityUserGuideDocumentController',
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
                    $translatePartialLoader.addPart('authorityUserGuideDocument');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('authority-user-guide-document-detail', {
            parent: 'authority-user-guide-document',
            url: '/authority-user-guide-document/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.authorityUserGuideDocument.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/authority-user-guide-document/authority-user-guide-document-detail.html',
                    controller: 'AuthorityUserGuideDocumentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('authorityUserGuideDocument');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AuthorityUserGuideDocument', function($stateParams, AuthorityUserGuideDocument) {
                    return AuthorityUserGuideDocument.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'authority-user-guide-document',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('authority-user-guide-document-detail.edit', {
            parent: 'authority-user-guide-document-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/authority-user-guide-document/authority-user-guide-document-dialog.html',
                    controller: 'AuthorityUserGuideDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AuthorityUserGuideDocument', function(AuthorityUserGuideDocument) {
                            return AuthorityUserGuideDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('authority-user-guide-document.new', {
            parent: 'authority-user-guide-document',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/authority-user-guide-document/authority-user-guide-document-dialog.html',
                    controller: 'AuthorityUserGuideDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                comments: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('authority-user-guide-document', null, { reload: 'authority-user-guide-document' });
                }, function() {
                    $state.go('authority-user-guide-document');
                });
            }]
        })
        .state('authority-user-guide-document.edit', {
            parent: 'authority-user-guide-document',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/authority-user-guide-document/authority-user-guide-document-dialog.html',
                    controller: 'AuthorityUserGuideDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AuthorityUserGuideDocument', function(AuthorityUserGuideDocument) {
                            return AuthorityUserGuideDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('authority-user-guide-document', null, { reload: 'authority-user-guide-document' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('authority-user-guide-document.delete', {
            parent: 'authority-user-guide-document',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/authority-user-guide-document/authority-user-guide-document-delete-dialog.html',
                    controller: 'AuthorityUserGuideDocumentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AuthorityUserGuideDocument', function(AuthorityUserGuideDocument) {
                            return AuthorityUserGuideDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('authority-user-guide-document', null, { reload: 'authority-user-guide-document' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
