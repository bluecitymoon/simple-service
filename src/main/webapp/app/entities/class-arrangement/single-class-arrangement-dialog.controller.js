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
        vm.classArrangements = [];
        vm.updatedClassArrangement = {
            targetClass: null
        };
        vm.allSelected = false;

        vm.toggleAll = function () {
            angular.forEach(vm.classArrangements, function (arrangement) {
                arrangement.selected = vm.allSelected;
            });
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

        function loadAllClassArrangements() {

            console.log($scope.dialogSchedule.classSchedule);

            if (!$scope.dialogSchedule.classSchedule.classId) {
                return;
            }

            ClassArrangement.query({"clazzId.equals": $scope.dialogSchedule.classSchedule.classId, size: 1000, page: 0, "startTime": $scope.dialogSchedule.start, "endTime": $scope.dialogSchedule.end}, function (arrangements) {
                vm.classArrangements = arrangements;
            });

        }
        loadAllClassArrangements();

        vm.reassignClassArrangements = function () {

            var selectedArrangements = [];
            angular.forEach(vm.classArrangements, function (arrangement) {

                if (arrangement.selected) {
                    selectedArrangements.push(arrangement);
                }
            });

            if (selectedArrangements.length == 0) {
                AlertService.error("未选中需要更换班级的排课");
            }

            if (vm.updatedClassArrangement.targetClass == null) {
                AlertService.error("未选中目标班级");
            }
            var request = {
                arrangements: selectedArrangements,
                newClass: vm.updatedClassArrangement.targetClass
            };

            ClassArrangement.reassignClassArrangements(request, function (data) {
                AlertService.success("更换成功!");
                $scope.$emit('simpleServiceApp:classArrangementsGenerated');
            }, function (error) {
                AlertService.showCommonError(error);
            });
        };

        vm.deleteClassArrangements = function () {

            var selectedArrangements = [];
            angular.forEach(vm.classArrangements, function (arrangement) {

                if (arrangement.selected) {
                    selectedArrangements.push(arrangement);
                }
            });

            if (selectedArrangements.length == 0) {
                AlertService.error("未选中需要更换班级的排课");
            }

            var request = {
                arrangements: selectedArrangements,
                newClass: null
            };

            ClassArrangement.deleteClassArrangements(request, function (data) {
                AlertService.success("删除成功!");
                $scope.$emit('simpleServiceApp:classArrangementsGenerated');

                loadAllClassArrangements();

            }, function (error) {
                AlertService.showCommonError(error);
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
