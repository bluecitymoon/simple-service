(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractStatusDetailController', ContractStatusDetailController);

    ContractStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContractStatus'];

    function ContractStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, ContractStatus) {
        var vm = this;

        vm.contractStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:contractStatusUpdate', function(event, result) {
            vm.contractStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
