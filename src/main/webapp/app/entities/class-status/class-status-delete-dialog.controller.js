(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassStatusDeleteController',ClassStatusDeleteController);

    ClassStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassStatus'];

    function ClassStatusDeleteController($uibModalInstance, entity, ClassStatus) {
        var vm = this;

        vm.classStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
