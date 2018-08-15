(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementDeleteController',ClassArrangementDeleteController);

    ClassArrangementDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassArrangement'];

    function ClassArrangementDeleteController($uibModalInstance, entity, ClassArrangement) {
        var vm = this;

        vm.classArrangement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassArrangement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
