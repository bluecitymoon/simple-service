(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CourseDetailController', CourseDetailController);

    CourseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Course', 'ClassCategoryBase'];

    function CourseDetailController($scope, $rootScope, $stateParams, previousState, entity, Course, ClassCategoryBase) {
        var vm = this;

        vm.course = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:courseUpdate', function(event, result) {
            vm.course = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
