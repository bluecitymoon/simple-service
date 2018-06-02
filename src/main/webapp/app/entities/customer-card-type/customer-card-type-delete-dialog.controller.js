(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardTypeDeleteController',CustomerCardTypeDeleteController);

    CustomerCardTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCardType'];

    function CustomerCardTypeDeleteController($uibModalInstance, entity, CustomerCardType) {
        var vm = this;

        vm.customerCardType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCardType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
