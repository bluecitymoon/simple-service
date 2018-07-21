(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customer', 'FreeClassRecord', 'CustomerCommunicationLog', 'CustomerCommunicationSchedule', 'ParseLinks', 'CustomerCard', 'CustomerTrackTask', 'AlertService', 'Student'];

    function CustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Customer, FreeClassRecord, CustomerCommunicationLog, CustomerCommunicationSchedule, ParseLinks, CustomerCard, CustomerTrackTask, AlertService, Student) {
        var vm = this;

        vm.customer = entity;
        vm.previousState = previousState.name;
        vm.logs = [];
        vm.classLevels = [
            {id: 1, value: "幼儿园"},
            {id: 2, value: "小学"},
            {id: 3, value: "初中"},
            {id: 4, value: "高中"}
        ];
        var unsubscribe = $rootScope.$on('simpleServiceApp:customerUpdate', function (event, result) {
            vm.customer = result;
        });
        var unsubscribeLogEvent = $rootScope.$on('simpleServiceApp:customerCommunicationLogUpdate', function (event, result) {
            vm.logs.push(result);
        });
        var unsubscribeLogGenerated = $rootScope.$on('simpleServiceApp:customerCommunicationNewLogGenerated', function (event, result) {
            loadCustomerlogs();
            loadAllSchedules();
        });

        var unsubscribeCardCreated = $rootScope.$on('simpleServiceApp:customerCardUpdate', function (event, result) {
            loadAllCards();
        });

        var unsubscribeTaskUpdate = $rootScope.$on('simpleServiceApp:taskUpdate', function (event, result) {
            loadAllCustomerTrackTasks();
        });

        $scope.$on('$destroy', unsubscribe);

        vm.closeTask = function (customerTrackTask) {
            CustomerTrackTask.closeTask({id: customerTrackTask.id}, function (response) {
                AlertService.success("操作成功！");

                loadAllCustomerTrackTasks();
            });
        };
        loadCustomerlogs();
        loadAllSchedules();
        loadAllCards();
        loadAllCustomerTrackTasks();
        loadStudents();

        function loadStudents() {

            Student.query({
                page: 0,
                size: 100,
                sort: 'id',
                'customerId.equals': vm.customer.id
            }, function (data) {
                vm.students = data;
            }, function (error) {
                //
            });
        }

        function loadAllCustomerTrackTasks() {
            CustomerTrackTask.getCustomerTrackTasks({cid: vm.customer.id},
                function (response) {
                    vm.customerTrackTasks = response;
                },
                function (error) {

                });
        }

        function loadCustomerlogs() {

            CustomerCommunicationLog.query({
                page: 0,
                size: 100,
                sort: 'id,desc',
                'customerId.equals': vm.customer.id
            }, function (data) {
                vm.logs = data;
            }, function (error) {
                //
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

        function loadAllCards() {

            var param = {
                page: 0,
                size: 100,
                sort: 'id',
                'customerId.equals': vm.customer.id
            };

            CustomerCard.query(param, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalCardItems = headers('X-Total-Count');
                vm.queryCardCount = vm.totalCardItems;
                vm.cards = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        };

        $scope.$on('$destroy', unsubscribe);
        $scope.$on('$destroy', unsubscribeLogEvent);
        $scope.$on('$destroy', unsubscribeLogGenerated);
        $scope.$on('$destroy', unsubscribeCardCreated);
        $scope.$on('$destroy', unsubscribeTaskUpdate);
    }
})();
