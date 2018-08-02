(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractPackageDetailController', ContractPackageDetailController);

    ContractPackageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContractPackage', 'ContractTemplate', 'CustomerCardType', 'AlertService'];

    function ContractPackageDetailController($scope, $rootScope, $stateParams, previousState, entity, ContractPackage, ContractTemplate, CustomerCardType, AlertService) {
        var vm = this;

        vm.contractPackage = entity;
        vm.previousState = previousState.name;

        loadAllTemplates();

        function loadAllTemplates () {
            ContractTemplate.getContractTemplatesByPackageId({id: vm.contractPackage.id}
            , onSuccess, onError);

            function onSuccess(data) {
                vm.contractTemplates = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        var unsubscribe = $rootScope.$on('simpleServiceApp:contractPackageUpdate', function(event, result) {
            vm.contractPackage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
