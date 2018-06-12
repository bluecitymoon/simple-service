(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderResourceLocationDeleteController',NewOrderResourceLocationDeleteController);

    NewOrderResourceLocationDeleteController.$inject = ['$uibModalInstance', 'entity', 'NewOrderResourceLocation'];

    function NewOrderResourceLocationDeleteController($uibModalInstance, entity, NewOrderResourceLocation) {
        var vm = this;

        vm.newOrderResourceLocation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NewOrderResourceLocation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
