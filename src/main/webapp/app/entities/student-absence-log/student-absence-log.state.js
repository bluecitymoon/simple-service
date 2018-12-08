(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-absence-log', {
            parent: 'entity',
            url: '/student-absence-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentAbsenceLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-absence-log/student-absence-logs.html',
                    controller: 'StudentAbsenceLogController',
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
                    $translatePartialLoader.addPart('studentAbsenceLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-absence-log-detail', {
            parent: 'student-absence-log',
            url: '/student-absence-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentAbsenceLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-absence-log/student-absence-log-detail.html',
                    controller: 'StudentAbsenceLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentAbsenceLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentAbsenceLog', function($stateParams, StudentAbsenceLog) {
                    return StudentAbsenceLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-absence-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-absence-log-detail.edit', {
            parent: 'student-absence-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-absence-log/student-absence-log-dialog.html',
                    controller: 'StudentAbsenceLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentAbsenceLog', function(StudentAbsenceLog) {
                            return StudentAbsenceLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-absence-log.new', {
            parent: 'student-absence-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-absence-log/student-absence-log-dialog.html',
                    controller: 'StudentAbsenceLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                comments: null,
                                regionId: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                classCount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-absence-log', null, { reload: 'student-absence-log' });
                }, function() {
                    $state.go('student-absence-log');
                });
            }]
        })
        .state('student-absence-log.edit', {
            parent: 'student-absence-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-absence-log/student-absence-log-dialog.html',
                    controller: 'StudentAbsenceLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentAbsenceLog', function(StudentAbsenceLog) {
                            return StudentAbsenceLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-absence-log', null, { reload: 'student-absence-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-absence-log.delete', {
            parent: 'student-absence-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-absence-log/student-absence-log-delete-dialog.html',
                    controller: 'StudentAbsenceLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentAbsenceLog', function(StudentAbsenceLog) {
                            return StudentAbsenceLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-absence-log', null, { reload: 'student-absence-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
