(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('UserGuideDocumentDialogController', UserGuideDocumentDialogController);

    UserGuideDocumentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserGuideDocument'];

    function UserGuideDocumentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserGuideDocument) {
        var vm = this;

        vm.userGuideDocument = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userGuideDocument.id !== null) {
                UserGuideDocument.update(vm.userGuideDocument, onSaveSuccess, onSaveError);
            } else {
                UserGuideDocument.save(vm.userGuideDocument, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:userGuideDocumentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
