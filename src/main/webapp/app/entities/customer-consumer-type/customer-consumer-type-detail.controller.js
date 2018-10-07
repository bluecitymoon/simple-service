(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerConsumerTypeDetailController', CustomerConsumerTypeDetailController);

    CustomerConsumerTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerConsumerType'];

    function CustomerConsumerTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerConsumerType) {
        var vm = this;

        vm.customerConsumerType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerConsumerTypeUpdate', function(event, result) {
            vm.customerConsumerType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
