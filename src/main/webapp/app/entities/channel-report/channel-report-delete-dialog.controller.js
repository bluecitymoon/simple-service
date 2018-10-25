(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ChannelReportDeleteController',ChannelReportDeleteController);

    ChannelReportDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChannelReport'];

    function ChannelReportDeleteController($uibModalInstance, entity, ChannelReport) {
        var vm = this;

        vm.channelReport = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChannelReport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
