(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementStatusDialogController', ClassArrangementStatusDialogController);

    ClassArrangementStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassArrangementStatus'];

    function ClassArrangementStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassArrangementStatus) {
        var vm = this;

        vm.classArrangementStatus = entity;
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
            if (vm.classArrangementStatus.id !== null) {
                ClassArrangementStatus.update(vm.classArrangementStatus, onSaveSuccess, onSaveError);
            } else {
                ClassArrangementStatus.save(vm.classArrangementStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classArrangementStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
