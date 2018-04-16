(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FreeClassRecordDeleteController',FreeClassRecordDeleteController);

    FreeClassRecordDeleteController.$inject = ['$uibModalInstance', 'entity', 'FreeClassRecord'];

    function FreeClassRecordDeleteController($uibModalInstance, entity, FreeClassRecord) {
        var vm = this;

        vm.freeClassRecord = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FreeClassRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
