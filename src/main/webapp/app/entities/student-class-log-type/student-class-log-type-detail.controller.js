(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentClassLogTypeDetailController', StudentClassLogTypeDetailController);

    StudentClassLogTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentClassLogType'];

    function StudentClassLogTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentClassLogType) {
        var vm = this;

        vm.studentClassLogType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentClassLogTypeUpdate', function(event, result) {
            vm.studentClassLogType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
