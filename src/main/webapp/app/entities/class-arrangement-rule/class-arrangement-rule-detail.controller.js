(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementRuleDetailController', ClassArrangementRuleDetailController);

    ClassArrangementRuleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClassArrangementRule', 'Product', 'ClassArrangementRuleLoopWay'];

    function ClassArrangementRuleDetailController($scope, $rootScope, $stateParams, previousState, entity, ClassArrangementRule, Product, ClassArrangementRuleLoopWay) {
        var vm = this;

        vm.classArrangementRule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:classArrangementRuleUpdate', function(event, result) {
            vm.classArrangementRule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
