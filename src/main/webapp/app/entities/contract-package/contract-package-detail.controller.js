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

        loadAllTemplates();

        function loadAllTemplates () {
            ContractTemplate.query({
                page: 0,
                size: 100
            }, onSuccess, onError);

            function onSuccess(data, headers) {

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
