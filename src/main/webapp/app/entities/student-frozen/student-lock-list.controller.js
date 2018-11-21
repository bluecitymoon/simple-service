(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentLockListController', StudentLockListController);

    StudentLockListController.$inject = [ '$uibModalInstance', 'entity',  'ClassArrangement', 'AlertService'];

    function StudentLockListController( $uibModalInstance, entity,  ClassArrangement, AlertService) {
        var vm = this;

        vm.frozen = entity;

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        function loadStudentFrozenClassArrangements() {

            ClassArrangement.getStudentFrozenArrangements({frozenId: vm.frozen.id},
                function (response) {
                    vm.classArrangements = response;


                }, function (error) {
                    AlertService.showCommonError(error);
                })
        }

        loadStudentFrozenClassArrangements();
    }
})();
