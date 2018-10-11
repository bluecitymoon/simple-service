(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractPackageTypeDetailController', ContractPackageTypeDetailController);

    ContractPackageTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContractPackageType'];

    function ContractPackageTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, ContractPackageType) {
        var vm = this;

        vm.contractPackageType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:contractPackageTypeUpdate', function(event, result) {
            vm.contractPackageType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
