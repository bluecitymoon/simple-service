(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractNatureDetailController', ContractNatureDetailController);

    ContractNatureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContractNature'];

    function ContractNatureDetailController($scope, $rootScope, $stateParams, previousState, entity, ContractNature) {
        var vm = this;

        vm.contractNature = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:contractNatureUpdate', function(event, result) {
            vm.contractNature = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
