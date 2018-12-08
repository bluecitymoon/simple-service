(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogTypeDeleteController',StudentClassLogTypeDeleteController);

    StudentClassLogTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentClassLogType'];

    function StudentClassLogTypeDeleteController($uibModalInstance, entity, StudentClassLogType) {
        var vm = this;

        vm.studentClassLogType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentClassLogType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
