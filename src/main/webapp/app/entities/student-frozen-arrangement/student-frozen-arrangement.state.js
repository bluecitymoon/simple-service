(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-frozen-arrangement', {
            parent: 'entity',
            url: '/student-frozen-arrangement?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentFrozenArrangement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-frozen-arrangement/student-frozen-arrangements.html',
                    controller: 'StudentFrozenArrangementController',
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
                    $translatePartialLoader.addPart('studentFrozenArrangement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-frozen-arrangement-detail', {
            parent: 'student-frozen-arrangement',
            url: '/student-frozen-arrangement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentFrozenArrangement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-frozen-arrangement/student-frozen-arrangement-detail.html',
                    controller: 'StudentFrozenArrangementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentFrozenArrangement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentFrozenArrangement', function($stateParams, StudentFrozenArrangement) {
                    return StudentFrozenArrangement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-frozen-arrangement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-frozen-arrangement-detail.edit', {
            parent: 'student-frozen-arrangement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-frozen-arrangement/student-frozen-arrangement-dialog.html',
                    controller: 'StudentFrozenArrangementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentFrozenArrangement', function(StudentFrozenArrangement) {
                            return StudentFrozenArrangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-frozen-arrangement.new', {
            parent: 'student-frozen-arrangement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-frozen-arrangement/student-frozen-arrangement-dialog.html',
                    controller: 'StudentFrozenArrangementDialogController',
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
                                active: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-frozen-arrangement', null, { reload: 'student-frozen-arrangement' });
                }, function() {
                    $state.go('student-frozen-arrangement');
                });
            }]
        })
        .state('student-frozen-arrangement.edit', {
            parent: 'student-frozen-arrangement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-frozen-arrangement/student-frozen-arrangement-dialog.html',
                    controller: 'StudentFrozenArrangementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentFrozenArrangement', function(StudentFrozenArrangement) {
                            return StudentFrozenArrangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-frozen-arrangement', null, { reload: 'student-frozen-arrangement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-frozen-arrangement.delete', {
            parent: 'student-frozen-arrangement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-frozen-arrangement/student-frozen-arrangement-delete-dialog.html',
                    controller: 'StudentFrozenArrangementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentFrozenArrangement', function(StudentFrozenArrangement) {
                            return StudentFrozenArrangement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-frozen-arrangement', null, { reload: 'student-frozen-arrangement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
