(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CourseDialogController', CourseDialogController);

    CourseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Course', 'ClassCategoryBase'];

    function CourseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Course, ClassCategoryBase) {
        var vm = this;

        vm.course = entity;
        vm.clear = clear;
        vm.save = save;
        vm.classcategorybases = ClassCategoryBase.query({size: 1000, page: 0});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.course.id !== null) {
                Course.update(vm.course, onSaveSuccess, onSaveError);
            } else {
                Course.save(vm.course, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:courseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
