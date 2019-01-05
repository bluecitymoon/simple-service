(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerCardCourseDetailController', CustomerCardCourseDetailController);

    CustomerCardCourseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomerCardCourse', 'CustomerCard', 'Course'];

    function CustomerCardCourseDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomerCardCourse, CustomerCard, Course) {
        var vm = this;

        vm.customerCardCourse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:customerCardCourseUpdate', function(event, result) {
            vm.customerCardCourse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
