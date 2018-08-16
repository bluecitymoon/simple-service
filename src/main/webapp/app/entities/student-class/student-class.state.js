(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-class', {
            parent: 'entity',
            url: '/student-class',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClass.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class/student-classes.html',
                    controller: 'StudentClassController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentClass');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-class-detail', {
            parent: 'student-class',
            url: '/student-class/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.studentClass.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-class/student-class-detail.html',
                    controller: 'StudentClassDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentClass');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentClass', function($stateParams, StudentClass) {
                    return StudentClass.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-class',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-class-detail.edit', {
            parent: 'student-class-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class/student-class-dialog.html',
                    controller: 'StudentClassDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClass', function(StudentClass) {
                            return StudentClass.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class.new', {
            parent: 'student-class',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class/student-class-dialog.html',
                    controller: 'StudentClassDialogController',
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
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-class', null, { reload: 'student-class' });
                }, function() {
                    $state.go('student-class');
                });
            }]
        })
        .state('student-class.edit', {
            parent: 'student-class',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class/student-class-dialog.html',
                    controller: 'StudentClassDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentClass', function(StudentClass) {
                            return StudentClass.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class', null, { reload: 'student-class' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-class.delete', {
            parent: 'student-class',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-class/student-class-delete-dialog.html',
                    controller: 'StudentClassDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentClass', function(StudentClass) {
                            return StudentClass.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-class', null, { reload: 'student-class' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
