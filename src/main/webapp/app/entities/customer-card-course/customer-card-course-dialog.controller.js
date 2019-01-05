(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardCourseDialogController', CustomerCardCourseDialogController);

    CustomerCardCourseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerCardCourse', 'CustomerCard', 'Course'];

    function CustomerCardCourseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerCardCourse, CustomerCard, Course) {
        var vm = this;

        vm.customerCardCourse = entity;
        vm.clear = clear;
        vm.save = save;
        vm.customercards = CustomerCard.query();
        vm.courses = Course.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerCardCourse.id !== null) {
                CustomerCardCourse.update(vm.customerCardCourse, onSaveSuccess, onSaveError);
            } else {
                CustomerCardCourse.save(vm.customerCardCourse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerCardCourseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
