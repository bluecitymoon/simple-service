(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CountNumberDeleteController',CountNumberDeleteController);

    CountNumberDeleteController.$inject = ['$uibModalInstance', 'entity', 'CountNumber'];

    function CountNumberDeleteController($uibModalInstance, entity, CountNumber) {
        var vm = this;

        vm.countNumber = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CountNumber.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
