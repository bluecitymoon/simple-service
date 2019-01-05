(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardCourseDeleteController',CustomerCardCourseDeleteController);

    CustomerCardCourseDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerCardCourse'];

    function CustomerCardCourseDeleteController($uibModalInstance, entity, CustomerCardCourse) {
        var vm = this;

        vm.customerCardCourse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerCardCourse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
