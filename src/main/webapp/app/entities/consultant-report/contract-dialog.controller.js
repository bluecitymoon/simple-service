(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ConsultantReportContractDetailDialogController', ConsultantReportContractDetailDialogController);

    ConsultantReportContractDetailDialogController.$inject = ['entity'];

    function ConsultantReportContractDetailDialogController (entity) {
        var vm = this;

        vm.contracts = entity;

    }
})();
