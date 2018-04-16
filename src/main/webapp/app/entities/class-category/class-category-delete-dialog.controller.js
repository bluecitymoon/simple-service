(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassCategoryDeleteController',ClassCategoryDeleteController);

    ClassCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassCategory'];

    function ClassCategoryDeleteController($uibModalInstance, entity, ClassCategory) {
        var vm = this;

        vm.classCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
