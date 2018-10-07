(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerConsumerLogDetailController', CustomerConsumerLogDetailController);

    CustomerConsumerLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerConsumerLog', 'CustomerConsumerType', 'Student'];

    function CustomerConsumerLogDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerConsumerLog, CustomerConsumerType, Student) {
        var vm = this;

        vm.customerConsumerLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerConsumerLogUpdate', function(event, result) {
            vm.customerConsumerLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
