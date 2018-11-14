(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentFrozenArrangementDeleteController',StudentFrozenArrangementDeleteController);

    StudentFrozenArrangementDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentFrozenArrangement'];

    function StudentFrozenArrangementDeleteController($uibModalInstance, entity, StudentFrozenArrangement) {
        var vm = this;

        vm.studentFrozenArrangement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentFrozenArrangement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
