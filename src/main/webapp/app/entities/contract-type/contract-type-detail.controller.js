(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractTypeDetailController', ContractTypeDetailController);

    ContractTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContractType'];

    function ContractTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, ContractType) {
        var vm = this;

        vm.contractType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:contractTypeUpdate', function(event, result) {
            vm.contractType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
