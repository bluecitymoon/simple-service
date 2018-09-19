(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ProductDialogController', ProductDialogController);

    ProductDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Product', 'ClassAgeLevel', 'Teacher', 'ClassRoom', 'Course'];

    function ProductDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Product, ClassAgeLevel, Teacher, ClassRoom, Course) {
        var vm = this;

        vm.product = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;         vm.datePickerOptions = {             showMeridian: false         };
        vm.save = save;
        vm.classagelevels = ClassAgeLevel.query({ page: 0,  size: 1000 });
        vm.teachers = Teacher.query({ page: 0,  size: 1000 });
        vm.courses = Course.query({ page: 0,  size: 1000 });

        loadClassRooms();
        function loadClassRooms() {

            ClassRoom.query({ page: 0,  size: 100 }, function (data) {
                vm.classrooms = data;

                if (vm.product.classRoomId) {
                    angular.forEach(vm.classrooms, function (room) {

                        if (room.id == vm.product.classRoomId) {
                            vm.product.classRoom = room;
                        }
                    })
                }

            });
        }
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.product.id !== null) {
                Product.update(vm.product, onSaveSuccess, onSaveError);
            } else {
                Product.save(vm.product, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:productUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.planedStartDate = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
