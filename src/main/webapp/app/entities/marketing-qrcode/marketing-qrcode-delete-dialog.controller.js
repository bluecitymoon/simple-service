(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketingQrcodeDeleteController',MarketingQrcodeDeleteController);

    MarketingQrcodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'MarketingQrcode'];

    function MarketingQrcodeDeleteController($uibModalInstance, entity, MarketingQrcode) {
        var vm = this;

        vm.marketingQrcode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MarketingQrcode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
