(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketChannelCategoryDialogController', MarketChannelCategoryDialogController);

    MarketChannelCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MarketChannelCategory'];

    function MarketChannelCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MarketChannelCategory) {
        var vm = this;

        vm.marketChannelCategory = entity;
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
            if (vm.marketChannelCategory.id !== null) {
                MarketChannelCategory.update(vm.marketChannelCategory, onSaveSuccess, onSaveError);
            } else {
                MarketChannelCategory.save(vm.marketChannelCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:marketChannelCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
