(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('NewOrderWechatUserInfoDetailController', NewOrderWechatUserInfoDetailController);

    NewOrderWechatUserInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NewOrderWechatUserInfo', 'FreeClassRecord'];

    function NewOrderWechatUserInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, NewOrderWechatUserInfo, FreeClassRecord) {
        var vm = this;

        vm.newOrderWechatUserInfo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:newOrderWechatUserInfoUpdate', function(event, result) {
            vm.newOrderWechatUserInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
