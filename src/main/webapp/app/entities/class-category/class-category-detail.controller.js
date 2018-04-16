(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassCategoryDetailController', ClassCategoryDetailController);

    ClassCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClassCategory', 'FreeClassRecord'];

    function ClassCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, ClassCategory, FreeClassRecord) {
        var vm = this;

        vm.classCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:classCategoryUpdate', function(event, result) {
            vm.classCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
