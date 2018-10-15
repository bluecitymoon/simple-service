(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('UserRegionDialogController', UserRegionDialogController);

    UserRegionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserRegion', 'User', 'Region'];

    function UserRegionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserRegion, User, Region) {
        var vm = this;

        vm.userRegion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.regions = Region.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userRegion.id !== null) {
                UserRegion.update(vm.userRegion, onSaveSuccess, onSaveError);
            } else {
                UserRegion.save(vm.userRegion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:userRegionUpdate', result);
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
