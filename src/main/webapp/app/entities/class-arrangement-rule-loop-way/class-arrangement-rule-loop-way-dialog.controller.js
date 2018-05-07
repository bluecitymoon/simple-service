(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementRuleLoopWayDialogController', ClassArrangementRuleLoopWayDialogController);

    ClassArrangementRuleLoopWayDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassArrangementRuleLoopWay'];

    function ClassArrangementRuleLoopWayDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassArrangementRuleLoopWay) {
        var vm = this;

        vm.classArrangementRuleLoopWay = entity;
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
            if (vm.classArrangementRuleLoopWay.id !== null) {
                ClassArrangementRuleLoopWay.update(vm.classArrangementRuleLoopWay, onSaveSuccess, onSaveError);
            } else {
                ClassArrangementRuleLoopWay.save(vm.classArrangementRuleLoopWay, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:classArrangementRuleLoopWayUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
