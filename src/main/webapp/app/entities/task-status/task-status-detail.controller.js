(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TaskStatusDetailController', TaskStatusDetailController);

    TaskStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TaskStatus'];

    function TaskStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, TaskStatus) {
        var vm = this;

        vm.taskStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:taskStatusUpdate', function(event, result) {
            vm.taskStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
