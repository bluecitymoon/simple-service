(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketChannelCategoryDeleteController',MarketChannelCategoryDeleteController);

    MarketChannelCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'MarketChannelCategory'];

    function MarketChannelCategoryDeleteController($uibModalInstance, entity, MarketChannelCategory) {
        var vm = this;

        vm.marketChannelCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MarketChannelCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
