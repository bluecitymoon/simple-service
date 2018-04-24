(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-class-record', {
            parent: 'entity',
            url: '/free-class-record?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.freeClassRecord.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-class-record/free-class-records.html',
                    controller: 'FreeClassRecordController',
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
                    $translatePartialLoader.addPart('freeClassRecord');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-class-record-detail', {
            parent: 'free-class-record',
            url: '/free-class-record/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.freeClassRecord.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-class-record/free-class-record-detail.html',
                    controller: 'FreeClassRecordDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freeClassRecord');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FreeClassRecord', function($stateParams, FreeClassRecord) {
                    return FreeClassRecord.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'free-class-record',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('free-class-record-detail.edit', {
            parent: 'free-class-record-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-class-record/free-class-record-dialog.html',
                    controller: 'FreeClassRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FreeClassRecord', function(FreeClassRecord) {
                            return FreeClassRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-class-record.new', {
            parent: 'free-class-record',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-class-record/free-class-record-dialog.html',
                    controller: 'FreeClassRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                personName: null,
                                contactPhoneNumber: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-class-record', null, { reload: 'free-class-record' });
                }, function() {
                    $state.go('free-class-record');
                });
            }]
        })
        .state('free-class-record.edit', {
            parent: 'free-class-record',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-class-record/free-class-record-dialog.html',
                    controller: 'FreeClassRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FreeClassRecord', function(FreeClassRecord) {
                            return FreeClassRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-class-record', null, { reload: 'free-class-record' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-class-record.delete', {
            parent: 'free-class-record',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-class-record/free-class-record-delete-dialog.html',
                    controller: 'FreeClassRecordDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FreeClassRecord', function(FreeClassRecord) {
                            return FreeClassRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-class-record', null, { reload: 'free-class-record' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
