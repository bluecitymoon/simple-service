(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-class-in-out-log', {
            parent: 'entity',
            url: '/student-class-in-out-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClassInOutLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class-in-out-log/student-class-in-out-logs.html',
                    controller: 'StudentClassInOutLogController',
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
                    $translatePartialLoader.addPart('studentClassInOutLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-class-in-out-log-detail', {
            parent: 'student-class-in-out-log',
            url: '/student-class-in-out-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClassInOutLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class-in-out-log/student-class-in-out-log-detail.html',
                    controller: 'StudentClassInOutLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentClassInOutLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentClassInOutLog', function($stateParams, StudentClassInOutLog) {
                    return StudentClassInOutLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-class-in-out-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-class-in-out-log-detail.edit', {
            parent: 'student-class-in-out-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-in-out-log/student-class-in-out-log-dialog.html',
                    controller: 'StudentClassInOutLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClassInOutLog', function(StudentClassInOutLog) {
                            return StudentClassInOutLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class-in-out-log.new', {
            parent: 'student-class-in-out-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-in-out-log/student-class-in-out-log-dialog.html',
                    controller: 'StudentClassInOutLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
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
                    $state.go('student-class-in-out-log', null, { reload: 'student-class-in-out-log' });
                }, function() {
                    $state.go('student-class-in-out-log');
                });
            }]
        })
        .state('student-class-in-out-log.edit', {
            parent: 'student-class-in-out-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-in-out-log/student-class-in-out-log-dialog.html',
                    controller: 'StudentClassInOutLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClassInOutLog', function(StudentClassInOutLog) {
                            return StudentClassInOutLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class-in-out-log', null, { reload: 'student-class-in-out-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class-in-out-log.delete', {
            parent: 'student-class-in-out-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class-in-out-log/student-class-in-out-log-delete-dialog.html',
                    controller: 'StudentClassInOutLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentClassInOutLog', function(StudentClassInOutLog) {
                            return StudentClassInOutLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class-in-out-log', null, { reload: 'student-class-in-out-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
