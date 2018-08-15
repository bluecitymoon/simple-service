(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CountNumberDialogController', CountNumberDialogController);

    CountNumberDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CountNumber', 'ClassArrangementRuleLoopWay'];

    function CountNumberDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CountNumber, ClassArrangementRuleLoopWay) {
        var vm = this;

        vm.countNumber = entity;
        vm.clear = clear;
        vm.save = save;
        vm.classarrangementruleloopways = ClassArrangementRuleLoopWay.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.countNumber.id !== null) {
                CountNumber.update(vm.countNumber, onSaveSuccess, onSaveError);
            } else {
                CountNumber.save(vm.countNumber, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:countNumberUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
