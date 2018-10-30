(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('AuthorityUserGuideDocumentDetailController', AuthorityUserGuideDocumentDetailController);

    AuthorityUserGuideDocumentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AuthorityUserGuideDocument', 'Authority', 'UserGuideDocument'];

    function AuthorityUserGuideDocumentDetailController($scope, $rootScope, $stateParams, previousState, entity, AuthorityUserGuideDocument, Authority, UserGuideDocument) {
        var vm = this;

        vm.authorityUserGuideDocument = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:authorityUserGuideDocumentUpdate', function(event, result) {
            vm.authorityUserGuideDocument = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
