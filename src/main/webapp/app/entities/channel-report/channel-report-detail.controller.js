(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ChannelReportDetailController', ChannelReportDetailController);

    ChannelReportDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChannelReport'];

    function ChannelReportDetailController($scope, $rootScope, $stateParams, previousState, entity, ChannelReport) {
        var vm = this;

        vm.channelReport = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:channelReportUpdate', function(event, result) {
            vm.channelReport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
