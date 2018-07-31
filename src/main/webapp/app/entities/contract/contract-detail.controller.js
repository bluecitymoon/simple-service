(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractDetailController', ContractDetailController);

    ContractDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contract', 'Student', 'Course', 'ContractStatus', 'Product', 'CustomerCard'];

    function ContractDetailController($scope, $rootScope, $stateParams, previousState, entity, Contract, Student, Course, ContractStatus, Product, CustomerCard) {
        var vm = this;

        vm.contract = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:contractUpdate', function(event, result) {
            vm.contract = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
