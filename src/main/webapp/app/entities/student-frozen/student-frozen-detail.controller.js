(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentFrozenDetailController', StudentFrozenDetailController);

    StudentFrozenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentFrozen', 'Student'];

    function StudentFrozenDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentFrozen, Student) {
        var vm = this;

        vm.studentFrozen = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentFrozenUpdate', function(event, result) {
            vm.studentFrozen = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
