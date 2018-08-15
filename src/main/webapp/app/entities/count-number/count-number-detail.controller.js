(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CountNumberDetailController', CountNumberDetailController);

    CountNumberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CountNumber', 'ClassArrangementRuleLoopWay'];

    function CountNumberDetailController($scope, $rootScope, $stateParams, previousState, entity, CountNumber, ClassArrangementRuleLoopWay) {
        var vm = this;

        vm.countNumber = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:countNumberUpdate', function(event, result) {
            vm.countNumber = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
