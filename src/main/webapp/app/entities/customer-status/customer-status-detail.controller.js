(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerStatusDetailController', CustomerStatusDetailController);

    CustomerStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerStatus'];

    function CustomerStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerStatus) {
        var vm = this;

        vm.customerStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerStatusUpdate', function(event, result) {
            vm.customerStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
