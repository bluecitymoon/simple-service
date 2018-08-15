(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementDetailController', ClassArrangementDetailController);

    ClassArrangementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClassArrangement', 'Teacher', 'ClassArrangementStatus'];

    function ClassArrangementDetailController($scope, $rootScope, $stateParams, previousState, entity, ClassArrangement, Teacher, ClassArrangementStatus) {
        var vm = this;

        vm.classArrangement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:classArrangementUpdate', function(event, result) {
            vm.classArrangement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
