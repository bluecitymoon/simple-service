(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'ClassAgeLevel', 'Teacher', 'ClassRoom', 'Course'];

    function ProductDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, ClassAgeLevel, Teacher, ClassRoom, Course) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
