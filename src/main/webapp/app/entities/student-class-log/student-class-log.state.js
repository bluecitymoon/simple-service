(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-class-log', {
            parent: 'entity',
            url: '/student-class-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClassLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class-log/student-class-logs.html',
                    controller: 'StudentClassLogController',
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
                    $translatePartialLoader.addPart('studentClassLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-class-log-detail', {
            parent: 'student-class-log',
            url: '/student-class-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClassLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class-log/student-class-log-detail.html',
                    controller: 'StudentClassLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentClassLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentClassLog', function($stateParams, StudentClassLog) {
                    return StudentClassLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-class-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-class-log-detail.edit', {
            parent: 'student-class-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log/student-class-log-dialog.html',
                    controller: 'StudentClassLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClassLog', function(StudentClassLog) {
                            return StudentClassLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class-log.new', {
            parent: 'student-class-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log/student-class-log-dialog.html',
                    controller: 'StudentClassLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                actualTakenDate: null,
                                comments: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                point: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-class-log', null, { reload: 'student-class-log' });
                }, function() {
                    $state.go('student-class-log');
                });
            }]
        })
        .state('student-class-log.edit', {
            parent: 'student-class-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log/student-class-log-dialog.html',
                    controller: 'StudentClassLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClassLog', function(StudentClassLog) {
                            return StudentClassLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class-log', null, { reload: 'student-class-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class-log.delete', {
            parent: 'student-class-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-log/student-class-log-delete-dialog.html',
                    controller: 'StudentClassLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentClassLog', function(StudentClassLog) {
                            return StudentClassLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class-log', null, { reload: 'student-class-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
