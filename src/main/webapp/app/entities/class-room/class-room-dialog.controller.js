(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassRoomDialogController', ClassRoomDialogController);

    ClassRoomDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassRoom'];

    function ClassRoomDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassRoom) {
        var vm = this;

        vm.classRoom = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classRoom.id !== null) {
                ClassRoom.update(vm.classRoom, onSaveSuccess, onSaveError);
            } else {
                ClassRoom.save(vm.classRoom, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classRoomUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
