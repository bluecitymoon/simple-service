(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassInOutLogDeleteController',StudentClassInOutLogDeleteController);

    StudentClassInOutLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentClassInOutLog'];

    function StudentClassInOutLogDeleteController($uibModalInstance, entity, StudentClassInOutLog) {
        var vm = this;

        vm.studentClassInOutLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentClassInOutLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
