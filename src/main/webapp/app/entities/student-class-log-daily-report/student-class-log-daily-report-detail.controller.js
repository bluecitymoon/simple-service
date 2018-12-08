(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogDailyReportDetailController', StudentClassLogDailyReportDetailController);

    StudentClassLogDailyReportDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentClassLogDailyReport'];

    function StudentClassLogDailyReportDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentClassLogDailyReport) {
        var vm = this;

        vm.studentClassLogDailyReport = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentClassLogDailyReportUpdate', function(event, result) {
            vm.studentClassLogDailyReport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
