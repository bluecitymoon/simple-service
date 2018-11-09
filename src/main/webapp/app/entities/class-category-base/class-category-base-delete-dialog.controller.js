(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassCategoryBaseDeleteController',ClassCategoryBaseDeleteController);

    ClassCategoryBaseDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassCategoryBase'];

    function ClassCategoryBaseDeleteController($uibModalInstance, entity, ClassCategoryBase) {
        var vm = this;

        vm.classCategoryBase = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassCategoryBase.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
