(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassDeleteController',StudentClassDeleteController);

    StudentClassDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentClass'];

    function StudentClassDeleteController($uibModalInstance, entity, StudentClass) {
        var vm = this;

        vm.studentClass = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentClass.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
