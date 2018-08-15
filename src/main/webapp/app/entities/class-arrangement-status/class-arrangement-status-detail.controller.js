(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassArrangementStatusDetailController', ClassArrangementStatusDetailController);

    ClassArrangementStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClassArrangementStatus'];

    function ClassArrangementStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, ClassArrangementStatus) {
        var vm = this;

        vm.classArrangementStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:classArrangementStatusUpdate', function(event, result) {
            vm.classArrangementStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
