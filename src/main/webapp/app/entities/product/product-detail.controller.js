(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$uibModal', '$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'ClassAgeLevel', 'Teacher', 'ClassRoom', 'Course', 'ClassArrangementRule', 'AlertService'];

    function ProductDetailController($uibModal, $scope, $rootScope, $stateParams, previousState, entity, Product, ClassAgeLevel, Teacher, ClassRoom, Course, ClassArrangementRule, AlertService) {
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
                        return arrangement;
                    }],
                    clazz: function () {
                        return vm.product;
                    }
                }
            }).result.then(function() {
                loadArrangementRule();
            }, function() {
                $state.go('^');
            });
        };

        vm.openAddDialog = function (arrangement) {
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
                            id: null
                        };
                    },
                    clazz: function () {
                        return vm.product;
                    }
                }
            }).result.then(function() {
                $state.go('class-arrangement-rule', null, { reload: 'class-arrangement-rule' });
            }, function() {
                $state.go('class-arrangement-rule');
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
