(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CollectionStatusDetailController', CollectionStatusDetailController);

    CollectionStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CollectionStatus'];

    function CollectionStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CollectionStatus) {
        var vm = this;

        vm.collectionStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:collectionStatusUpdate', function(event, result) {
            vm.collectionStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
