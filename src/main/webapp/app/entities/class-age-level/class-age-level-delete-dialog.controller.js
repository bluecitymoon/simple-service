(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassAgeLevelDeleteController',ClassAgeLevelDeleteController);

    ClassAgeLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassAgeLevel'];

    function ClassAgeLevelDeleteController($uibModalInstance, entity, ClassAgeLevel) {
        var vm = this;

        vm.classAgeLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassAgeLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
