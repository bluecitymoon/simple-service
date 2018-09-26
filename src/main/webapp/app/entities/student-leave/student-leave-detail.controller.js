(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentLeaveDetailController', StudentLeaveDetailController);

    StudentLeaveDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentLeave', 'Student', 'ClassArrangement'];

    function StudentLeaveDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentLeave, Student, ClassArrangement) {
        var vm = this;

        vm.studentLeave = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentLeaveUpdate', function(event, result) {
            vm.studentLeave = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
