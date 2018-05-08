(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'ClassAgeLevel', 'Teacher', 'ClassRoom', 'Course', 'ClassArrangementRule', 'AlertService'];

    function ProductDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, ClassAgeLevel, Teacher, ClassRoom, Course, ClassArrangementRule, AlertService) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;
        vm.classArrangementRule = {};
        loadArrangementRule();

        function loadArrangementRule() {
            ClassArrangementRule.query({"targetClassId.equals": vm.product.id}, function (data) {

                if (data && data.length > 0) {
                    vm.classArrangementRule = data[0];
                }

                console.log(vm.classArrangementRule);

            },function (error) {
                AlertService.error(error);
            })
        }

        var unsubscribe = $rootScope.$on('simpleServiceApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
