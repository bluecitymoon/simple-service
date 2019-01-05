(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-card-course', {
            parent: 'entity',
            url: '/customer-card-course?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCardCourse.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-card-course/customer-card-courses.html',
                    controller: 'CustomerCardCourseController',
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
                    $translatePartialLoader.addPart('customerCardCourse');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-card-course-detail', {
            parent: 'customer-card-course',
            url: '/customer-card-course/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerCardCourse.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-card-course/customer-card-course-detail.html',
                    controller: 'CustomerCardCourseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerCardCourse');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerCardCourse', function($stateParams, CustomerCardCourse) {
                    return CustomerCardCourse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-card-course',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-card-course-detail.edit', {
            parent: 'customer-card-course-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card-course/customer-card-course-dialog.html',
                    controller: 'CustomerCardCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCardCourse', function(CustomerCardCourse) {
                            return CustomerCardCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-card-course.new', {
            parent: 'customer-card-course',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card-course/customer-card-course-dialog.html',
                    controller: 'CustomerCardCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-card-course', null, { reload: 'customer-card-course' });
                }, function() {
                    $state.go('customer-card-course');
                });
            }]
        })
        .state('customer-card-course.edit', {
            parent: 'customer-card-course',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card-course/customer-card-course-dialog.html',
                    controller: 'CustomerCardCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerCardCourse', function(CustomerCardCourse) {
                            return CustomerCardCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-card-course', null, { reload: 'customer-card-course' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-card-course.delete', {
            parent: 'customer-card-course',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-card-course/customer-card-course-delete-dialog.html',
                    controller: 'CustomerCardCourseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerCardCourse', function(CustomerCardCourse) {
                            return CustomerCardCourse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-card-course', null, { reload: 'customer-card-course' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
