(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('AssetDeleteController',AssetDeleteController);

    AssetDeleteController.$inject = ['$uibModalInstance', 'entity', 'Asset'];

    function AssetDeleteController($uibModalInstance, entity, Asset) {
        var vm = this;

        vm.asset = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Asset.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
