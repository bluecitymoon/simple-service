(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TimetableItemDeleteController',TimetableItemDeleteController);

    TimetableItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'TimetableItem'];

    function TimetableItemDeleteController($uibModalInstance, entity, TimetableItem) {
        var vm = this;

        vm.timetableItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TimetableItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
