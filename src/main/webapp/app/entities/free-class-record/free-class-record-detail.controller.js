(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FreeClassRecordDetailController', FreeClassRecordDetailController);

    FreeClassRecordDetailController.$inject = ['$state', '$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FreeClassRecord', 'MarketChannelCategory','AlertService', 'ClassCategory', 'User'];

    function FreeClassRecordDetailController($state, $scope, $rootScope, $stateParams, previousState, entity, FreeClassRecord, MarketChannelCategory, AlertService, ClassCategory, User) {
        var vm = this;

        vm.freeClassRecord = entity;
        vm.previousState = previousState.name;

        vm.assignToSales = function () {

            FreeClassRecord.connectCustomer({id : vm.freeClassRecord.id},

                function (result) {

                    $state.go('customer-detail', {id: result.id});

                    AlertService.success("操作成功！");

                }, function (error) {

                    AlertService.error("出错了。");
                });
        };

        // vm.users = User.query({ page: 0,  size: 1000 });
        //
        // vm.searchPersonWithKeyword = function (keyword) {
        //
        //     console.log("searching people with keyword " + keyword);
        // };

        function save () {
            vm.isSaving = true;
            if (vm.freeClassRecord.id !== null) {
                FreeClassRecord.update(vm.freeClassRecord, onSaveSuccess, onSaveError);
            } else {
                FreeClassRecord.save(vm.freeClassRecord, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;

            // AlertService.success("分配成功！")
        }

        function onSaveError () {
            vm.isSaving = false;
            AlertService.error("Assign failed.");
        }

        var unsubscribe = $rootScope.$on('simpleServiceApp:freeClassRecordUpdate', function(event, result) {
            vm.freeClassRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
