(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassRoomDeleteController',ClassRoomDeleteController);

    ClassRoomDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassRoom'];

    function ClassRoomDeleteController($uibModalInstance, entity, ClassRoom) {
        var vm = this;

        vm.classRoom = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassRoom.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
