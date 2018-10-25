(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ChannelReportDialogController', ChannelReportDialogController);

    ChannelReportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChannelReport'];

    function ChannelReportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChannelReport) {
        var vm = this;

        vm.channelReport = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.channelReport.id !== null) {
                ChannelReport.update(vm.channelReport, onSaveSuccess, onSaveError);
            } else {
                ChannelReport.save(vm.channelReport, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:channelReportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
