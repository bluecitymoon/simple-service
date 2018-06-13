(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('PaymentTypeDetailController', PaymentTypeDetailController);

    PaymentTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PaymentType'];

    function PaymentTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, PaymentType) {
        var vm = this;

        vm.paymentType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:paymentTypeUpdate', function(event, result) {
            vm.paymentType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
