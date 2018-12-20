(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ConsultantReportContractDetailDialogController', ConsultantReportContractDetailDialogController);

    ConsultantReportContractDetailDialogController.$inject = ['entity', '$uibModalInstance'];

    function ConsultantReportContractDetailDialogController (entity, $uibModalInstance) {
        var vm = this;

        vm.contracts = entity;
        vm.clear = function (){
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
