(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ClassAgeLevelDetailController', ClassAgeLevelDetailController);

    ClassAgeLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClassAgeLevel'];

    function ClassAgeLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, ClassAgeLevel) {
        var vm = this;

        vm.classAgeLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:classAgeLevelUpdate', function(event, result) {
            vm.classAgeLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
