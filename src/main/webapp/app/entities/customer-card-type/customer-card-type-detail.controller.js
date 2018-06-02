(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardTypeDetailController', CustomerCardTypeDetailController);

    CustomerCardTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCardType'];

    function CustomerCardTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCardType) {
        var vm = this;

        vm.customerCardType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCardTypeUpdate', function(event, result) {
            vm.customerCardType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
