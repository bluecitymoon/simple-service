(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-leave', {
            parent: 'entity',
            url: '/student-leave?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentLeave.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-leave/student-leaves.html',
                    controller: 'StudentLeaveController',
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
                    $translatePartialLoader.addPart('studentLeave');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-leave-detail', {
            parent: 'student-leave',
            url: '/student-leave/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentLeave.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-leave/student-leave-detail.html',
                    controller: 'StudentLeaveDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentLeave');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentLeave', function($stateParams, StudentLeave) {
                    return StudentLeave.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-leave',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-leave-detail.edit', {
            parent: 'student-leave-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-leave/student-leave-dialog.html',
                    controller: 'StudentLeaveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentLeave', function(StudentLeave) {
                            return StudentLeave.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-leave.new', {
            parent: 'student-leave',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-leave/student-leave-dialog.html',
                    controller: 'StudentLeaveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                balance: null,
                                calculateStartDate: null,
                                calculateEndDate: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-leave', null, { reload: 'student-leave' });
                }, function() {
                    $state.go('student-leave');
                });
            }]
        })
        .state('student-leave.edit', {
            parent: 'student-leave',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-leave/student-leave-dialog.html',
                    controller: 'StudentLeaveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentLeave', function(StudentLeave) {
                            return StudentLeave.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-leave', null, { reload: 'student-leave' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-leave.delete', {
            parent: 'student-leave',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-leave/student-leave-delete-dialog.html',
                    controller: 'StudentLeaveDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentLeave', function(StudentLeave) {
                            return StudentLeave.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-leave', null, { reload: 'student-leave' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
