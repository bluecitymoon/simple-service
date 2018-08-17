(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassInOutLogDetailController', StudentClassInOutLogDetailController);

    StudentClassInOutLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentClassInOutLog', 'Student', 'Product'];

    function StudentClassInOutLogDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentClassInOutLog, Student, Product) {
        var vm = this;

        vm.studentClassInOutLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentClassInOutLogUpdate', function(event, result) {
            vm.studentClassInOutLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
