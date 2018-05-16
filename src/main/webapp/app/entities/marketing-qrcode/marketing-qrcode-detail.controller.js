(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketingQrcodeDetailController', MarketingQrcodeDetailController);

    MarketingQrcodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MarketingQrcode', 'User'];

    function MarketingQrcodeDetailController($scope, $rootScope, $stateParams, previousState, entity, MarketingQrcode, User) {
        var vm = this;

        vm.marketingQrcode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:marketingQrcodeUpdate', function(event, result) {
            vm.marketingQrcode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
