(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketingQrcodeDialogController', MarketingQrcodeDialogController);

    MarketingQrcodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MarketingQrcode', 'User'];

    function MarketingQrcodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MarketingQrcode, User) {
        var vm = this;

        vm.marketingQrcode = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query({ page: 0,  size: 1000 });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.marketingQrcode.id !== null) {
                MarketingQrcode.update(vm.marketingQrcode, onSaveSuccess, onSaveError);
            } else {
                MarketingQrcode.save(vm.marketingQrcode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:marketingQrcodeUpdate', result);
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
