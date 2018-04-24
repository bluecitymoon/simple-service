(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('FreeClassRecordDetailController', FreeClassRecordDetailController);

    FreeClassRecordDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FreeClassRecord', 'MarketChannelCategory', 'ClassCategory'];

    function FreeClassRecordDetailController($scope, $rootScope, $stateParams, previousState, entity, FreeClassRecord, MarketChannelCategory, ClassCategory) {
        var vm = this;

        vm.freeClassRecord = entity;
        vm.previousState = previousState.name;

        vm.assignToSales = function () {

        };

        var unsubscribe = $rootScope.$on('simpleServiceApp:freeClassRecordUpdate', function(event, result) {
            vm.freeClassRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
