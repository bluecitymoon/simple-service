(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogDetailController', StudentClassLogDetailController);

    StudentClassLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentClassLog', 'Student', 'ClassArrangement'];

    function StudentClassLogDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentClassLog, Student, ClassArrangement) {
        var vm = this;

        vm.studentClassLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentClassLogUpdate', function(event, result) {
            vm.studentClassLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
