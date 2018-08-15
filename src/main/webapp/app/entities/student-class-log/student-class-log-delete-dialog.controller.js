(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogDeleteController',StudentClassLogDeleteController);

    StudentClassLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentClassLog'];

    function StudentClassLogDeleteController($uibModalInstance, entity, StudentClassLog) {
        var vm = this;

        vm.studentClassLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentClassLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
