(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TimetableItemDetailController', TimetableItemDetailController);

    TimetableItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TimetableItem', 'Product', 'TimeSegment'];

    function TimetableItemDetailController($scope, $rootScope, $stateParams, previousState, entity, TimetableItem, Product, TimeSegment) {
        var vm = this;

        vm.timetableItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:timetableItemUpdate', function(event, result) {
            vm.timetableItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
