(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerConsumerTypeDeleteController',CustomerConsumerTypeDeleteController);

    CustomerConsumerTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerConsumerType'];

    function CustomerConsumerTypeDeleteController($uibModalInstance, entity, CustomerConsumerType) {
        var vm = this;

        vm.customerConsumerType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerConsumerType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
