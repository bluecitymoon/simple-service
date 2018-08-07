(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCollectionLogDeleteController',CustomerCollectionLogDeleteController);

    CustomerCollectionLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCollectionLog'];

    function CustomerCollectionLogDeleteController($uibModalInstance, entity, CustomerCollectionLog) {
        var vm = this;

        vm.customerCollectionLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCollectionLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
