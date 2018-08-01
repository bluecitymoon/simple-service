(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractPackageDetailController', ContractPackageDetailController);

    ContractPackageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContractPackage', 'ContractTemplate', 'CustomerCardType'];

    function ContractPackageDetailController($scope, $rootScope, $stateParams, previousState, entity, ContractPackage, ContractTemplate, CustomerCardType) {
        var vm = this;

        vm.contractPackage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:contractPackageUpdate', function(event, result) {
            vm.contractPackage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
