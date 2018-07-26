(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractTemplateDetailController', ContractTemplateDetailController);

    ContractTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContractTemplate', 'CustomerCardType', 'ContractNature'];

    function ContractTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, ContractTemplate, CustomerCardType, ContractNature) {
        var vm = this;

        vm.contractTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:contractTemplateUpdate', function(event, result) {
            vm.contractTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
