(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('SystemVariableDetailController', SystemVariableDetailController);

    SystemVariableDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SystemVariable'];

    function SystemVariableDetailController($scope, $rootScope, $stateParams, previousState, entity, SystemVariable) {
        var vm = this;

        vm.systemVariable = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:systemVariableUpdate', function(event, result) {
            vm.systemVariable = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
