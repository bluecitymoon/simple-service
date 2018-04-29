(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderWechatUserInfoDeleteController',NewOrderWechatUserInfoDeleteController);

    NewOrderWechatUserInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'NewOrderWechatUserInfo'];

    function NewOrderWechatUserInfoDeleteController($uibModalInstance, entity, NewOrderWechatUserInfo) {
        var vm = this;

        vm.newOrderWechatUserInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NewOrderWechatUserInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
