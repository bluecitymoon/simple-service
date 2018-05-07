(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementRuleDeleteController',ClassArrangementRuleDeleteController);

    ClassArrangementRuleDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassArrangementRule'];

    function ClassArrangementRuleDeleteController($uibModalInstance, entity, ClassArrangementRule) {
        var vm = this;

        vm.classArrangementRule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassArrangementRule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
