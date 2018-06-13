(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerTrackTaskDeleteController',CustomerTrackTaskDeleteController);

    CustomerTrackTaskDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerTrackTask'];

    function CustomerTrackTaskDeleteController($uibModalInstance, entity, CustomerTrackTask) {
        var vm = this;

        vm.customerTrackTask = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerTrackTask.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
