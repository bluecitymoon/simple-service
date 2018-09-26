(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentLeaveDeleteController',StudentLeaveDeleteController);

    StudentLeaveDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentLeave'];

    function StudentLeaveDeleteController($uibModalInstance, entity, StudentLeave) {
        var vm = this;

        vm.studentLeave = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentLeave.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
