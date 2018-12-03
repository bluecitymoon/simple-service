(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ScheduledTaskLogDetailController', ScheduledTaskLogDetailController);

    ScheduledTaskLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ScheduledTaskLog'];

    function ScheduledTaskLogDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ScheduledTaskLog) {
        var vm = this;

        vm.scheduledTaskLog = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('simpleServiceApp:scheduledTaskLogUpdate', function(event, result) {
            vm.scheduledTaskLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
