(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementRuleDialogController', ClassArrangementRuleDialogController);

    ClassArrangementRuleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'clazz', 'ClassArrangementRule', 'Product', 'ClassArrangementRuleLoopWay', 'CountNumber'];

    function ClassArrangementRuleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, clazz, ClassArrangementRule, Product, ClassArrangementRuleLoopWay, CountNumber) {
        var vm = this;

        vm.classArrangementRule = entity;

        if (!clazz) {
            vm.classArrangementRule.targetClass = clazz;
        }
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.products = Product.query({ page: 0,  size: 1000 });
        vm.classarrangementruleloopways = ClassArrangementRuleLoopWay.query({ page: 0,  size: 1000 });
        vm.selectedWeekDay = {};
        vm.countNumbers = [];
        // vm.weekdays = [
        //     {label: '星期一', value: 1},
        //     {label: '星期二', value: 2},
        //     {label: '星期三', value: 3},
        //     {label: '星期四', value: 4},
        //     {label: '星期五', value: 5},
        //     {label: '星期六', value: 6},
        //     {label: '星期天', value: 7}
        // ];

        $scope.$watch('vm.classArrangementRule.loopWay', function (newVal, oldVal) {

            if (newVal) {
                CountNumber.getByLoopWay({id: newVal.id}, function (data) {
                    vm.countNumbers = data;
                })
            }

        });

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
        vm.datePickerOpenStatus.estimateEndDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
