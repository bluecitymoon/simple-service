(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerScheduleStatusDetailController', CustomerScheduleStatusDetailController);

    CustomerScheduleStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerScheduleStatus'];

    function CustomerScheduleStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerScheduleStatus) {
        var vm = this;

        vm.customerScheduleStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerScheduleStatusUpdate', function(event, result) {
            vm.customerScheduleStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
