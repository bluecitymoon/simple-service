(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardDeleteController',CustomerCardDeleteController);

    CustomerCardDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCard'];

    function CustomerCardDeleteController($uibModalInstance, entity, CustomerCard) {
        var vm = this;

        vm.customerCard = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
