(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FreeClassPlanDetailController', FreeClassPlanDetailController);

    FreeClassPlanDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FreeClassPlan'];

    function FreeClassPlanDetailController($scope, $rootScope, $stateParams, previousState, entity, FreeClassPlan) {
        var vm = this;

        vm.freeClassPlan = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:freeClassPlanUpdate', function(event, result) {
            vm.freeClassPlan = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
