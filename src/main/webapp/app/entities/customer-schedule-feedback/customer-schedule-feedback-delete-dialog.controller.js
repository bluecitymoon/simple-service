(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerScheduleFeedbackDeleteController',CustomerScheduleFeedbackDeleteController);

    CustomerScheduleFeedbackDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerScheduleFeedback'];

    function CustomerScheduleFeedbackDeleteController($uibModalInstance, entity, CustomerScheduleFeedback) {
        var vm = this;

        vm.customerScheduleFeedback = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerScheduleFeedback.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
