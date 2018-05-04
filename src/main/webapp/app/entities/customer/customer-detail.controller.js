(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customer', 'FreeClassRecord', 'CustomerCommunicationLog'];

    function CustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Customer, FreeClassRecord, CustomerCommunicationLog) {
        var vm = this;

        vm.customer = entity;
        vm.previousState = previousState.name;
        vm.logs = [];

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        var unsubscribeLogEvent = $rootScope.$on('simpleServiceApp:customerCommunicationLogUpdate', function(event, result) {
            vm.logs.push(result);
        });
        var unsubscribeLogGenerated = $rootScope.$on('simpleServiceApp:customerCommunicationNewLogGenerated', function(event, result) {
            loadCustomerlogs();
        });


        $scope.$on('$destroy', unsubscribe);
        loadCustomerlogs();

        function loadCustomerlogs() {

            CustomerCommunicationLog.query({
                page: 0,
                size: 100,
                sort: 'id',
                'customerId.equals': vm.customer.id
            }, function (data) {
                vm.logs = data;
            }, function (error) {

            });

        }
        $scope.$on('$destroy', unsubscribe);
        $scope.$on('$destroy', unsubscribeLogEvent);
        $scope.$on('$destroy', unsubscribeLogGenerated);
    }
})();
