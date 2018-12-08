(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-class-log-type', {
            parent: 'entity',
            url: '/student-class-log-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClassLogType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class-log-type/student-class-log-types.html',
                    controller: 'StudentClassLogTypeController',
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
                    $translatePartialLoader.addPart('studentClassLogType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-class-log-type-detail', {
            parent: 'student-class-log-type',
            url: '/student-class-log-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClassLogType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class-log-type/student-class-log-type-detail.html',
                    controller: 'StudentClassLogTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentClassLogType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentClassLogType', function($stateParams, StudentClassLogType) {
                    return StudentClassLogType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-class-log-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-class-log-type-detail.edit', {
            parent: 'student-class-log-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log-type/student-class-log-type-dialog.html',
                    controller: 'StudentClassLogTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClassLogType', function(StudentClassLogType) {
                            return StudentClassLogType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class-log-type.new', {
            parent: 'student-class-log-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log-type/student-class-log-type-dialog.html',
                    controller: 'StudentClassLogTypeDialogController',
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
                    $state.go('student-class-log-type', null, { reload: 'student-class-log-type' });
                }, function() {
                    $state.go('student-class-log-type');
                });
            }]
        })
        .state('student-class-log-type.edit', {
            parent: 'student-class-log-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log-type/student-class-log-type-dialog.html',
                    controller: 'StudentClassLogTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClassLogType', function(StudentClassLogType) {
                            return StudentClassLogType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class-log-type', null, { reload: 'student-class-log-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class-log-type.delete', {
            parent: 'student-class-log-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log-type/student-class-log-type-delete-dialog.html',
                    controller: 'StudentClassLogTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentClassLogType', function(StudentClassLogType) {
                            return StudentClassLogType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class-log-type', null, { reload: 'student-class-log-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
