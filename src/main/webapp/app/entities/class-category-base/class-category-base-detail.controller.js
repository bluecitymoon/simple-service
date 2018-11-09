(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassCategoryBaseDetailController', ClassCategoryBaseDetailController);

    ClassCategoryBaseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClassCategoryBase'];

    function ClassCategoryBaseDetailController($scope, $rootScope, $stateParams, previousState, entity, ClassCategoryBase) {
        var vm = this;

        vm.classCategoryBase = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:classCategoryBaseUpdate', function(event, result) {
            vm.classCategoryBase = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
