(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CollectionDeleteController',CollectionDeleteController);

    CollectionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Collection'];

    function CollectionDeleteController($uibModalInstance, entity, Collection) {
        var vm = this;

        vm.collection = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Collection.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
