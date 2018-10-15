(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('UserRegionDetailController', UserRegionDetailController);

    UserRegionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserRegion', 'User', 'Region'];

    function UserRegionDetailController($scope, $rootScope, $stateParams, previousState, entity, UserRegion, User, Region) {
        var vm = this;

        vm.userRegion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:userRegionUpdate', function(event, result) {
            vm.userRegion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
