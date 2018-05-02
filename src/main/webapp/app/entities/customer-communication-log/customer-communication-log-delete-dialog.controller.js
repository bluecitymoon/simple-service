(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationLogDeleteController',CustomerCommunicationLogDeleteController);

    CustomerCommunicationLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCommunicationLog'];

    function CustomerCommunicationLogDeleteController($uibModalInstance, entity, CustomerCommunicationLog) {
        var vm = this;

        vm.customerCommunicationLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCommunicationLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
