(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ConsultantReportDialogController', ConsultantReportDialogController);

    ConsultantReportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ConsultantReport'];

    function ConsultantReportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ConsultantReport) {
        var vm = this;

        vm.consultantReport = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.consultantReport.id !== null) {
                ConsultantReport.update(vm.consultantReport, onSaveSuccess, onSaveError);
            } else {
                ConsultantReport.save(vm.consultantReport, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:consultantReportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.weekFromDate = false;
        vm.datePickerOpenStatus.weekEndDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
