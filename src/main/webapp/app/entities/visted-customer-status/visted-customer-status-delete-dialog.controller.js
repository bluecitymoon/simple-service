(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('VistedCustomerStatusDeleteController',VistedCustomerStatusDeleteController);

    VistedCustomerStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'VistedCustomerStatus'];

    function VistedCustomerStatusDeleteController($uibModalInstance, entity, VistedCustomerStatus) {
        var vm = this;

        vm.vistedCustomerStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VistedCustomerStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
