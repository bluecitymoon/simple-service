(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FreeClassPlanDeleteController',FreeClassPlanDeleteController);

    FreeClassPlanDeleteController.$inject = ['$uibModalInstance', 'entity', 'FreeClassPlan'];

    function FreeClassPlanDeleteController($uibModalInstance, entity, FreeClassPlan) {
        var vm = this;

        vm.freeClassPlan = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FreeClassPlan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
