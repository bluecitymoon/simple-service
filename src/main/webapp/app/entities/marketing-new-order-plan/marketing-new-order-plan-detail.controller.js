(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketingNewOrderPlanDetailController', MarketingNewOrderPlanDetailController);

    MarketingNewOrderPlanDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MarketingNewOrderPlan', 'User'];

    function MarketingNewOrderPlanDetailController($scope, $rootScope, $stateParams, previousState, entity, MarketingNewOrderPlan, User) {
        var vm = this;

        vm.marketingNewOrderPlan = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:marketingNewOrderPlanUpdate', function(event, result) {
            vm.marketingNewOrderPlan = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
