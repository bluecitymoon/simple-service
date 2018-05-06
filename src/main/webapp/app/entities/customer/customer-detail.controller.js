(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customer', 'FreeClassRecord', 'CustomerCommunicationLog', 'CustomerCommunicationSchedule', 'ParseLinks'];

    function CustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Customer, FreeClassRecord, CustomerCommunicationLog, CustomerCommunicationSchedule, ParseLinks) {
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
            loadAllSchedules();
        });


        $scope.$on('$destroy', unsubscribe);
        loadCustomerlogs();
        loadAllSchedules();

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

        function loadAllSchedules() {

            var param = {
                page: 0,
                size: 100,
                sort: 'id',
                'customerId.equals': vm.customer.id
            };

            CustomerCommunicationSchedule.query(param, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.customerCommunicationSchedules = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }

        };
        $scope.$on('$destroy', unsubscribe);
        $scope.$on('$destroy', unsubscribeLogEvent);
        $scope.$on('$destroy', unsubscribeLogGenerated);
    }
})();
