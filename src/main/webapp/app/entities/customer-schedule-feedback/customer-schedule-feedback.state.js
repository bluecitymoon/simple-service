(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-schedule-feedback', {
            parent: 'entity',
            url: '/customer-schedule-feedback?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerScheduleFeedback.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-schedule-feedback/customer-schedule-feedbacks.html',
                    controller: 'CustomerScheduleFeedbackController',
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
                    $translatePartialLoader.addPart('customerScheduleFeedback');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-schedule-feedback-detail', {
            parent: 'customer-schedule-feedback',
            url: '/customer-schedule-feedback/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.customerScheduleFeedback.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-schedule-feedback/customer-schedule-feedback-detail.html',
                    controller: 'CustomerScheduleFeedbackDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerScheduleFeedback');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerScheduleFeedback', function($stateParams, CustomerScheduleFeedback) {
                    return CustomerScheduleFeedback.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-schedule-feedback',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-schedule-feedback-detail.edit', {
            parent: 'customer-schedule-feedback-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-schedule-feedback/customer-schedule-feedback-dialog.html',
                    controller: 'CustomerScheduleFeedbackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerScheduleFeedback', function(CustomerScheduleFeedback) {
                            return CustomerScheduleFeedback.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-schedule-feedback.new', {
            parent: 'customer-schedule-feedback',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-schedule-feedback/customer-schedule-feedback-dialog.html',
                    controller: 'CustomerScheduleFeedbackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                giftCode: null,
                                giftStatus: null,
                                createdBy: null,
                                createdDate: null,
                                lastModifiedBy: null,
                                lastModifiedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-schedule-feedback', null, { reload: 'customer-schedule-feedback' });
                }, function() {
                    $state.go('customer-schedule-feedback');
                });
            }]
        })
        .state('customer-schedule-feedback.edit', {
            parent: 'customer-schedule-feedback',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-schedule-feedback/customer-schedule-feedback-dialog.html',
                    controller: 'CustomerScheduleFeedbackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerScheduleFeedback', function(CustomerScheduleFeedback) {
                            return CustomerScheduleFeedback.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-schedule-feedback', null, { reload: 'customer-schedule-feedback' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-schedule-feedback.delete', {
            parent: 'customer-schedule-feedback',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-schedule-feedback/customer-schedule-feedback-delete-dialog.html',
                    controller: 'CustomerScheduleFeedbackDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerScheduleFeedback', function(CustomerScheduleFeedback) {
                            return CustomerScheduleFeedback.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-schedule-feedback', null, { reload: 'customer-schedule-feedback' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
