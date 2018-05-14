(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('AdDialogController', AdDialogController);

    AdDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ad'];

    function AdDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ad) {
        var vm = this;

        vm.ad = entity;
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
            if (vm.ad.id !== null) {
                Ad.update(vm.ad, onSaveSuccess, onSaveError);
            } else {
                Ad.save(vm.ad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:adUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
