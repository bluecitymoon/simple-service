(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerConsumerLogDeleteController',CustomerConsumerLogDeleteController);

    CustomerConsumerLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerConsumerLog'];

    function CustomerConsumerLogDeleteController($uibModalInstance, entity, CustomerConsumerLog) {
        var vm = this;

        vm.customerConsumerLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerConsumerLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
