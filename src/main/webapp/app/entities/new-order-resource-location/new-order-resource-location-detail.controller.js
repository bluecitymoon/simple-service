(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderResourceLocationDetailController', NewOrderResourceLocationDetailController);

    NewOrderResourceLocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NewOrderResourceLocation'];

    function NewOrderResourceLocationDetailController($scope, $rootScope, $stateParams, previousState, entity, NewOrderResourceLocation) {
        var vm = this;

        vm.newOrderResourceLocation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:newOrderResourceLocationUpdate', function(event, result) {
            vm.newOrderResourceLocation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
