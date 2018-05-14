(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('AdDeleteController',AdDeleteController);

    AdDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ad'];

    function AdDeleteController($uibModalInstance, entity, Ad) {
        var vm = this;

        vm.ad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
