(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentAbsenceLogDetailController', StudentAbsenceLogDetailController);

    StudentAbsenceLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentAbsenceLog', 'Student', 'ClassArrangement'];

    function StudentAbsenceLogDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentAbsenceLog, Student, ClassArrangement) {
        var vm = this;

        vm.studentAbsenceLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentAbsenceLogUpdate', function(event, result) {
            vm.studentAbsenceLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
