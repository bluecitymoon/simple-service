(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerTrackTaskDetailController', CustomerTrackTaskDetailController);

    CustomerTrackTaskDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerTrackTask', 'Customer', 'Task'];

    function CustomerTrackTaskDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerTrackTask, Customer, Task) {
        var vm = this;

        vm.customerTrackTask = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerTrackTaskUpdate', function(event, result) {
            vm.customerTrackTask = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
