(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customer', 'FreeClassRecord'];

    function CustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Customer, FreeClassRecord) {
        var vm = this;

        vm.customer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
