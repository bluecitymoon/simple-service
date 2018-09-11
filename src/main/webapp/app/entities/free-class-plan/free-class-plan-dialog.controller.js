(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FreeClassPlanDialogController', FreeClassPlanDialogController);

    FreeClassPlanDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FreeClassPlan'];

    function FreeClassPlanDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FreeClassPlan) {
        var vm = this;

        vm.freeClassPlan = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freeClassPlan.id !== null) {
                FreeClassPlan.update(vm.freeClassPlan, onSaveSuccess, onSaveError);
            } else {
                FreeClassPlan.save(vm.freeClassPlan, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:freeClassPlanUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.planDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
