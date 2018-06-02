(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardDetailController', CustomerCardDetailController);

    CustomerCardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCard', 'Customer', 'CustomerCardType'];

    function CustomerCardDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCard, Customer, CustomerCardType) {
        var vm = this;

        vm.customerCard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCardUpdate', function(event, result) {
            vm.customerCard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
