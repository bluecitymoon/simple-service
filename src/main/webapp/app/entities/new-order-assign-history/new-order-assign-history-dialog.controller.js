(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderAssignHistoryDialogController', NewOrderAssignHistoryDialogController);

    NewOrderAssignHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NewOrderAssignHistory', 'FreeClassRecord'];

    function NewOrderAssignHistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NewOrderAssignHistory, FreeClassRecord) {
        var vm = this;

        vm.newOrderAssignHistory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        vm.freeclassrecords = FreeClassRecord.query({ page: 0,  size: 1000 });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.newOrderAssignHistory.id !== null) {
                NewOrderAssignHistory.update(vm.newOrderAssignHistory, onSaveSuccess, onSaveError);
            } else {
                NewOrderAssignHistory.save(vm.newOrderAssignHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:newOrderAssignHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
