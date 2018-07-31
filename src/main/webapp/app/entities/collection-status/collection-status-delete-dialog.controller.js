(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CollectionStatusDeleteController',CollectionStatusDeleteController);

    CollectionStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CollectionStatus'];

    function CollectionStatusDeleteController($uibModalInstance, entity, CollectionStatus) {
        var vm = this;

        vm.collectionStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CollectionStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
