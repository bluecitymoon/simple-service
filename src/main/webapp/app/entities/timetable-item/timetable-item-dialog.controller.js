(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TimetableItemDialogController', TimetableItemDialogController);

    TimetableItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimetableItem', 'Product', 'TimeSegment'];

    function TimetableItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TimetableItem, Product, TimeSegment) {
        var vm = this;

        vm.timetableItem = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.products = Product.query();
        vm.timesegments = TimeSegment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.timetableItem.id !== null) {
                TimetableItem.update(vm.timetableItem, onSaveSuccess, onSaveError);
            } else {
                TimetableItem.save(vm.timetableItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:timetableItemUpdate', result);
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
