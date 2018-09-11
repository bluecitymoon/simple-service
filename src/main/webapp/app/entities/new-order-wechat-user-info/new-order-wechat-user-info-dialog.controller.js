(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderWechatUserInfoDialogController', NewOrderWechatUserInfoDialogController);

    NewOrderWechatUserInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NewOrderWechatUserInfo', 'FreeClassRecord'];

    function NewOrderWechatUserInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NewOrderWechatUserInfo, FreeClassRecord) {
        var vm = this;

        vm.newOrderWechatUserInfo = entity;
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
            if (vm.newOrderWechatUserInfo.id !== null) {
                NewOrderWechatUserInfo.update(vm.newOrderWechatUserInfo, onSaveSuccess, onSaveError);
            } else {
                NewOrderWechatUserInfo.save(vm.newOrderWechatUserInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:newOrderWechatUserInfoUpdate', result);
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
