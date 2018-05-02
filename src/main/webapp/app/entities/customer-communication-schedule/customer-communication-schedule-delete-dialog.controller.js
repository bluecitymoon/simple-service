(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCommunicationScheduleDeleteController',CustomerCommunicationScheduleDeleteController);

    CustomerCommunicationScheduleDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCommunicationSchedule'];

    function CustomerCommunicationScheduleDeleteController($uibModalInstance, entity, CustomerCommunicationSchedule) {
        var vm = this;

        vm.customerCommunicationSchedule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCommunicationSchedule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
