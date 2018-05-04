(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerStatusDeleteController',CustomerStatusDeleteController);

    CustomerStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerStatus'];

    function CustomerStatusDeleteController($uibModalInstance, entity, CustomerStatus) {
        var vm = this;

        vm.customerStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
