(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('UserRegionDeleteController',UserRegionDeleteController);

    UserRegionDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserRegion'];

    function UserRegionDeleteController($uibModalInstance, entity, UserRegion) {
        var vm = this;

        vm.userRegion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserRegion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
