(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementRuleLoopWayDeleteController',ClassArrangementRuleLoopWayDeleteController);

    ClassArrangementRuleLoopWayDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassArrangementRuleLoopWay'];

    function ClassArrangementRuleLoopWayDeleteController($uibModalInstance, entity, ClassArrangementRuleLoopWay) {
        var vm = this;

        vm.classArrangementRuleLoopWay = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassArrangementRuleLoopWay.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
