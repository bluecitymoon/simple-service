(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentAbsenceLogDeleteController',StudentAbsenceLogDeleteController);

    StudentAbsenceLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentAbsenceLog'];

    function StudentAbsenceLogDeleteController($uibModalInstance, entity, StudentAbsenceLog) {
        var vm = this;

        vm.studentAbsenceLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentAbsenceLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
