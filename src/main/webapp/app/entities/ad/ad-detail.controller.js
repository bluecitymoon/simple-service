(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('AdDetailController', AdDetailController);

    AdDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ad'];

    function AdDetailController($scope, $rootScope, $stateParams, previousState, entity, Ad) {
        var vm = this;

        vm.ad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:adUpdate', function(event, result) {
            vm.ad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
