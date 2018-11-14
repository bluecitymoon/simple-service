(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentFrozenDeleteController',StudentFrozenDeleteController);

    StudentFrozenDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentFrozen'];

    function StudentFrozenDeleteController($uibModalInstance, entity, StudentFrozen) {
        var vm = this;

        vm.studentFrozen = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentFrozen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
