(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FinanceCategoryDetailController', FinanceCategoryDetailController);

    FinanceCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FinanceCategory'];

    function FinanceCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, FinanceCategory) {
        var vm = this;

        vm.financeCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:financeCategoryUpdate', function(event, result) {
            vm.financeCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
