(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderAssignHistoryDeleteController',NewOrderAssignHistoryDeleteController);

    NewOrderAssignHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'NewOrderAssignHistory'];

    function NewOrderAssignHistoryDeleteController($uibModalInstance, entity, NewOrderAssignHistory) {
        var vm = this;

        vm.newOrderAssignHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NewOrderAssignHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
