(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketingNewOrderPlanDialogController', MarketingNewOrderPlanDialogController);

    MarketingNewOrderPlanDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MarketingNewOrderPlan', 'User'];

    function MarketingNewOrderPlanDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MarketingNewOrderPlan, User) {
        var vm = this;

        vm.marketingNewOrderPlan = entity;
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
            if (vm.marketingNewOrderPlan.id !== null) {
                MarketingNewOrderPlan.update(vm.marketingNewOrderPlan, onSaveSuccess, onSaveError);
            } else {
                MarketingNewOrderPlan.save(vm.marketingNewOrderPlan, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:marketingNewOrderPlanUpdate', result);
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
