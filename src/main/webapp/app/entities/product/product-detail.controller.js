(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$uibModal', '$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'ClassAgeLevel', 'Teacher', 'ClassRoom', 'Course', 'ClassArrangementRule', 'AlertService', 'DateUtils'];

    function ProductDetailController($uibModal, $scope, $rootScope, $stateParams, previousState, entity, Product, ClassAgeLevel, Teacher, ClassRoom, Course, ClassArrangementRule, AlertService, DateUtils) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;
        vm.classArrangementRule = {};
        loadArrangementRule();

        function loadArrangementRule() {
            ClassArrangementRule.query({"targetClassId.equals": vm.product.id}, function (data) {

                if (data && data.length > 0) {
                    vm.classArrangementRules = data;
                }

                console.log(vm.classArrangementRule);

            },function (error) {
                AlertService.error(error);
            })
        }

        vm.openEditDialog = function (arrangement) {
            $uibModal.open({
                templateUrl: 'app/entities/class-arrangement-rule/class-arrangement-rule-dialog.html',
                controller: 'ClassArrangementRuleDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: ['ClassArrangementRule', function(ClassArrangementRule) {
                        var data = null;
                        if (arrangement) {
                            data = angular.fromJson(arrangement);
                            data.estimateStartDate = DateUtils.convertDateTimeFromServer(data.estimateStartDate);
                            data.estimateEndDate = DateUtils.convertDateTimeFromServer(data.estimateEndDate);
                        }
                        return data;
                    }],
                    clazz: function () {

                        return vm.product;
                    }
                }
            }).result.then(function() {
                loadArrangementRule();
            }, function() {
            });
        };

        vm.openAddDialog = function () {
            $uibModal.open({
                templateUrl: 'app/entities/class-arrangement-rule/class-arrangement-rule-dialog.html',
                controller: 'ClassArrangementRuleDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            estimateStartDate: null,
                            estimateStartTime: null,
                            estimateEndTime: null,
                            maxLoopCount: null,
                            id: null,
                            targetClass: vm.product
                        };
                    },
                    clazz: function () {
                        return vm.product;
                    }
                }
            }).result.then(function() {
                loadArrangementRule();
            }, function() {

            });
        };

        vm.generateClassSchedule = function (arrangement) {

        };

        var unsubscribe = $rootScope.$on('simpleServiceApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
