(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('UserGuideDocumentDetailController', UserGuideDocumentDetailController);

    UserGuideDocumentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserGuideDocument'];

    function UserGuideDocumentDetailController($scope, $rootScope, $stateParams, previousState, entity, UserGuideDocument) {
        var vm = this;

        vm.userGuideDocument = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:userGuideDocumentUpdate', function(event, result) {
            vm.userGuideDocument = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
