(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('StudentFrozenArrangementDetailController', StudentFrozenArrangementDetailController);

    StudentFrozenArrangementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentFrozenArrangement', 'Student', 'ClassArrangement', 'StudentFrozen'];

    function StudentFrozenArrangementDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentFrozenArrangement, Student, ClassArrangement, StudentFrozen) {
        var vm = this;

        vm.studentFrozenArrangement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:studentFrozenArrangementUpdate', function(event, result) {
            vm.studentFrozenArrangement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
