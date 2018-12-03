(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ScheduledTaskLogDeleteController',ScheduledTaskLogDeleteController);

    ScheduledTaskLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'ScheduledTaskLog'];

    function ScheduledTaskLogDeleteController($uibModalInstance, entity, ScheduledTaskLog) {
        var vm = this;

        vm.scheduledTaskLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ScheduledTaskLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
