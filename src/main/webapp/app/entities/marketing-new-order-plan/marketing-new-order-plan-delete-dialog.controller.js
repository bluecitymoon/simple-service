(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketingNewOrderPlanDeleteController',MarketingNewOrderPlanDeleteController);

    MarketingNewOrderPlanDeleteController.$inject = ['$uibModalInstance', 'entity', 'MarketingNewOrderPlan'];

    function MarketingNewOrderPlanDeleteController($uibModalInstance, entity, MarketingNewOrderPlan) {
        var vm = this;

        vm.marketingNewOrderPlan = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MarketingNewOrderPlan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
