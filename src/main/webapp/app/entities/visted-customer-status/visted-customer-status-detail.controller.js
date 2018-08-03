(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('VistedCustomerStatusDetailController', VistedCustomerStatusDetailController);

    VistedCustomerStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VistedCustomerStatus'];

    function VistedCustomerStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, VistedCustomerStatus) {
        var vm = this;

        vm.vistedCustomerStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:vistedCustomerStatusUpdate', function(event, result) {
            vm.vistedCustomerStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
