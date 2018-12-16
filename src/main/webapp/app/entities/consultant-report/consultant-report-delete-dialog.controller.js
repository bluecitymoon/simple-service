(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ConsultantReportDeleteController',ConsultantReportDeleteController);

    ConsultantReportDeleteController.$inject = ['$uibModalInstance', 'entity', 'ConsultantReport'];

    function ConsultantReportDeleteController($uibModalInstance, entity, ConsultantReport) {
        var vm = this;

        vm.consultantReport = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ConsultantReport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
