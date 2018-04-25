(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderAssignHistoryDetailController', NewOrderAssignHistoryDetailController);

    NewOrderAssignHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NewOrderAssignHistory', 'FreeClassRecord'];

    function NewOrderAssignHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, NewOrderAssignHistory, FreeClassRecord) {
        var vm = this;

        vm.newOrderAssignHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:newOrderAssignHistoryUpdate', function(event, result) {
            vm.newOrderAssignHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
