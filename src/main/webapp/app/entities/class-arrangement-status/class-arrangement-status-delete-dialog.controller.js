(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementStatusDeleteController',ClassArrangementStatusDeleteController);

    ClassArrangementStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassArrangementStatus'];

    function ClassArrangementStatusDeleteController($uibModalInstance, entity, ClassArrangementStatus) {
        var vm = this;

        vm.classArrangementStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassArrangementStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
