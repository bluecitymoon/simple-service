(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ConsultantReportDetailController', ConsultantReportDetailController);

    ConsultantReportDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ConsultantReport'];

    function ConsultantReportDetailController($scope, $rootScope, $stateParams, previousState, entity, ConsultantReport) {
        var vm = this;

        vm.consultantReport = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:consultantReportUpdate', function(event, result) {
            vm.consultantReport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
