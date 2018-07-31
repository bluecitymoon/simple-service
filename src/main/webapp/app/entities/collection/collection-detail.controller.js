(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CollectionDetailController', CollectionDetailController);

    CollectionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Collection', 'FinanceCategory', 'PaymentType', 'CollectionStatus'];

    function CollectionDetailController($scope, $rootScope, $stateParams, previousState, entity, Collection, FinanceCategory, PaymentType, CollectionStatus) {
        var vm = this;

        vm.collection = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:collectionUpdate', function(event, result) {
            vm.collection = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
