(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TaskStatusDeleteController',TaskStatusDeleteController);

    TaskStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'TaskStatus'];

    function TaskStatusDeleteController($uibModalInstance, entity, TaskStatus) {
        var vm = this;

        vm.taskStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TaskStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
