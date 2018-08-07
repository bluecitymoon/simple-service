(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCollectionLogDetailController', CustomerCollectionLogDetailController);

    CustomerCollectionLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCollectionLog', 'Collection', 'Customer'];

    function CustomerCollectionLogDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCollectionLog, Collection, Customer) {
        var vm = this;

        vm.customerCollectionLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCollectionLogUpdate', function(event, result) {
            vm.customerCollectionLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
