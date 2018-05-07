(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementRuleLoopWayDetailController', ClassArrangementRuleLoopWayDetailController);

    ClassArrangementRuleLoopWayDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClassArrangementRuleLoopWay'];

    function ClassArrangementRuleLoopWayDetailController($scope, $rootScope, $stateParams, previousState, entity, ClassArrangementRuleLoopWay) {
        var vm = this;

        vm.classArrangementRuleLoopWay = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:classArrangementRuleLoopWayUpdate', function(event, result) {
            vm.classArrangementRuleLoopWay = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
