(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('PaymentTypeDeleteController',PaymentTypeDeleteController);

    PaymentTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PaymentType'];

    function PaymentTypeDeleteController($uibModalInstance, entity, PaymentType) {
        var vm = this;

        vm.paymentType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PaymentType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
