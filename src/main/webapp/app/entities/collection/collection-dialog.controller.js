(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CollectionDialogController', CollectionDialogController);

    CollectionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Collection', 'FinanceCategory', 'PaymentType', 'CollectionStatus'];

    function CollectionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Collection, FinanceCategory, PaymentType, CollectionStatus) {
        var vm = this;

        vm.collection = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        vm.financecategories = FinanceCategory.query();
        vm.paymenttypes = PaymentType.query();
        vm.collectionstatuses = CollectionStatus.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.collection.id !== null) {
                Collection.update(vm.collection, onSaveSuccess, onSaveError);
            } else {
                Collection.save(vm.collection, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:collectionUpdate', result);
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
