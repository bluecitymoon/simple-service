(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('channel-report', {
            parent: 'entity',
            url: '/channel-report?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.channelReport.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/channel-report/channel-reports.html',
                    controller: 'ChannelReportController',
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
                    $translatePartialLoader.addPart('channelReport');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('channel-report-detail', {
            parent: 'channel-report',
            url: '/channel-report/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'simpleServiceApp.channelReport.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/channel-report/channel-report-detail.html',
                    controller: 'ChannelReportDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('channelReport');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ChannelReport', function($stateParams, ChannelReport) {
                    return ChannelReport.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'channel-report',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('channel-report-detail.edit', {
            parent: 'channel-report-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/channel-report/channel-report-dialog.html',
                    controller: 'ChannelReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChannelReport', function(ChannelReport) {
                            return ChannelReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('channel-report.new', {
            parent: 'channel-report',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/channel-report/channel-report-dialog.html',
                    controller: 'ChannelReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                month: null,
                                year: null,
                                channelId: null,
                                channelName: null,
                                visitedCustomerCount: null,
                                cardCount: null,
                                contractCount: null,
                                moneyCollected: null,
                                contractMadeRate: null,
                                regionId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('channel-report', null, { reload: 'channel-report' });
                }, function() {
                    $state.go('channel-report');
                });
            }]
        })
        .state('channel-report.edit', {
            parent: 'channel-report',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/channel-report/channel-report-dialog.html',
                    controller: 'ChannelReportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChannelReport', function(ChannelReport) {
                            return ChannelReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('channel-report', null, { reload: 'channel-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('channel-report.delete', {
            parent: 'channel-report',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/channel-report/channel-report-delete-dialog.html',
                    controller: 'ChannelReportDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChannelReport', function(ChannelReport) {
                            return ChannelReport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('channel-report', null, { reload: 'channel-report' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
