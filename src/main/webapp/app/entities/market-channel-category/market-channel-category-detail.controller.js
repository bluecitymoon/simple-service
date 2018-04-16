(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('MarketChannelCategoryDetailController', MarketChannelCategoryDetailController);

    MarketChannelCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MarketChannelCategory'];

    function MarketChannelCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, MarketChannelCategory) {
        var vm = this;

        vm.marketChannelCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:marketChannelCategoryUpdate', function(event, result) {
            vm.marketChannelCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
