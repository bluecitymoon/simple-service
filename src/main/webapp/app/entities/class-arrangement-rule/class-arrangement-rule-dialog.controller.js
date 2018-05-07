(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementRuleDialogController', ClassArrangementRuleDialogController);

    ClassArrangementRuleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassArrangementRule', 'Product', 'ClassArrangementRuleLoopWay'];

    function ClassArrangementRuleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassArrangementRule, Product, ClassArrangementRuleLoopWay) {
        var vm = this;

        vm.classArrangementRule = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.products = Product.query();
        vm.classarrangementruleloopways = ClassArrangementRuleLoopWay.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classArrangementRule.id !== null) {
                ClassArrangementRule.update(vm.classArrangementRule, onSaveSuccess, onSaveError);
            } else {
                ClassArrangementRule.save(vm.classArrangementRule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classArrangementRuleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.estimateStartDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
