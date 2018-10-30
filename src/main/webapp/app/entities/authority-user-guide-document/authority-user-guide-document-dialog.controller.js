(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('AuthorityUserGuideDocumentDialogController', AuthorityUserGuideDocumentDialogController);

    AuthorityUserGuideDocumentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AuthorityUserGuideDocument', 'Authority', 'UserGuideDocument'];

    function AuthorityUserGuideDocumentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AuthorityUserGuideDocument, Authority, UserGuideDocument) {
        var vm = this;

        vm.authorityUserGuideDocument = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.authorities = Authority.query();
        vm.userguidedocuments = UserGuideDocument.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.authorityUserGuideDocument.id !== null) {
                AuthorityUserGuideDocument.update(vm.authorityUserGuideDocument, onSaveSuccess, onSaveError);
            } else {
                AuthorityUserGuideDocument.save(vm.authorityUserGuideDocument, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:authorityUserGuideDocumentUpdate', result);
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
