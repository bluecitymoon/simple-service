(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TimeSegmentDeleteController',TimeSegmentDeleteController);

    TimeSegmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'TimeSegment'];

    function TimeSegmentDeleteController($uibModalInstance, entity, TimeSegment) {
        var vm = this;

        vm.timeSegment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TimeSegment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
