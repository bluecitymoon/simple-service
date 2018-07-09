(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('PaymentDetailController', PaymentDetailController);

    PaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Payment', 'User', 'PaymentType', 'FinanceCategory'];

    function PaymentDetailController($scope, $rootScope, $stateParams, previousState, entity, Payment, User, PaymentType, FinanceCategory) {
        var vm = this;

        vm.payment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:paymentUpdate', function(event, result) {
            vm.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
