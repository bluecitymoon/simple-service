(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('EducationLevelDetailController', EducationLevelDetailController);

    EducationLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EducationLevel'];

    function EducationLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, EducationLevel) {
        var vm = this;

        vm.educationLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:educationLevelUpdate', function(event, result) {
            vm.educationLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
