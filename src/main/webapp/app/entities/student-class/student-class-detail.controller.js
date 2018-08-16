(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassDetailController', StudentClassDetailController);

    StudentClassDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentClass', 'Student', 'Product'];

    function StudentClassDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentClass, Student, Product) {
        var vm = this;

        vm.studentClass = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentClassUpdate', function(event, result) {
            vm.studentClass = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
