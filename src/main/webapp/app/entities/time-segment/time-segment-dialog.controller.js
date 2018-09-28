(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TimeSegmentDialogController', TimeSegmentDialogController);

    TimeSegmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimeSegment'];

    function TimeSegmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TimeSegment) {
        var vm = this;

        vm.timeSegment = entity;
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
            if (vm.timeSegment.id !== null) {
                TimeSegment.update(vm.timeSegment, onSaveSuccess, onSaveError);
            } else {
                TimeSegment.save(vm.timeSegment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:timeSegmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
