(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('TimeSegmentDetailController', TimeSegmentDetailController);

    TimeSegmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TimeSegment'];

    function TimeSegmentDetailController($scope, $rootScope, $stateParams, previousState, entity, TimeSegment) {
        var vm = this;

        vm.timeSegment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:timeSegmentUpdate', function(event, result) {
            vm.timeSegment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
