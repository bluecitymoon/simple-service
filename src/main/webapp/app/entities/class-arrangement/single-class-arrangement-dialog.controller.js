(function () {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('SingleClassArrangmentDialogController', SingleClassArrangmentDialogController);

    SingleClassArrangmentDialogController.$inject = ['$rootScope', '$uibModal', '$timeout', '$scope', '$uibModalInstance', 'ClassArrangement', 'Product',  'AlertService', 'CountNumber'];

    function SingleClassArrangmentDialogController($rootScope, $uibModal, $timeout, $scope, $uibModalInstance, ClassArrangement, Product,  AlertService,  CountNumber) {
        var vm = this;

        console.log($scope.dialogSchedule);
        vm.classArrangementRule = {
            estimateStartTime: $scope.dialogSchedule.start,
            estimateEndTime: $scope.dialogSchedule.end,
            consumeClassCount: 2
        };
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.datePickerOptions = {
            showMeridian: false
        };
        vm.save = save;

        vm.classroomId = $scope.dialogSchedule.classSchedule.classroomId;

        CountNumber.query({"name.equals": $scope.dialogSchedule.weekdayName}, function (data) {
            vm.classArrangementRule.countNumber = data[0];
        });

        function loadClassInClassRoom() {

            Product.query({"size": 100, "classRoomId.equals": vm.classroomId},
                function (data) {
                    vm.products = data;
                    if (!vm.products || vm.products.length == 0) {
                        AlertService.warning("该教室没有可选班级");
                    }

                });
        }

        loadClassInClassRoom();

        vm.openAddClassDialog = function () {

            $uibModal.open({
                templateUrl: 'app/entities/product/product-dialog.html',
                controller: 'ProductDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            code: null,
                            minutesPerClass: null,
                            planedStartDate: null,
                            comments: null,
                            createdBy: null,
                            createdDate: null,
                            lastModifiedBy: null,
                            lastModifiedDate: null,
                            id: null,
                            classRoomId: vm.classroomId
                        };
                    }
                }
            }).result.then(function() {

            }, function() {
                // $state.go('product');
            });
        };

        var unsubscribe = $rootScope.$on('simpleServiceApp:productUpdate', function(event, result) {
            loadClassInClassRoom();
        });

        $scope.$on('$destroy', unsubscribe);

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {

            console.log(vm.classArrangementRule);

            if (!vm.classArrangementRule.estimateStartDate) {
                AlertService.error("请输入排课开始日期");
                return;
            }

            if (!vm.classArrangementRule.estimateEndDate) {
                AlertService.error("请输入排课结束日期");
                return;
            }

            if (!vm.classArrangementRule.targetClass) {
                AlertService.error("请选择班级");
                return;
            }

            ClassArrangement.createClassSchedule(vm.classArrangementRule,onSaveSuccess, onSaveError);
        }

        function onSaveSuccess() {
            AlertService.success("生成成功！");

            $uibModalInstance.close();

            $scope.$emit('simpleServiceApp:classArrangementsGenerated');
            vm.isSaving = false;
        }

        function onSaveError(error) {
            vm.isSaving = false;
            if (error.data && error.data.detail) {

                AlertService.error(error.data.detail);
            }
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
