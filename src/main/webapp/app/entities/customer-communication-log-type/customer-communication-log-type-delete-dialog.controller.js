(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationLogTypeDeleteController',CustomerCommunicationLogTypeDeleteController);

    CustomerCommunicationLogTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCommunicationLogType'];

    function CustomerCommunicationLogTypeDeleteController($uibModalInstance, entity, CustomerCommunicationLogType) {
        var vm = this;

        vm.customerCommunicationLogType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCommunicationLogType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
