(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogDailyReportDeleteController',StudentClassLogDailyReportDeleteController);

    StudentClassLogDailyReportDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentClassLogDailyReport'];

    function StudentClassLogDailyReportDeleteController($uibModalInstance, entity, StudentClassLogDailyReport) {
        var vm = this;

        vm.studentClassLogDailyReport = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentClassLogDailyReport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
