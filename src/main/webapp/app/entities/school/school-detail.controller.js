(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('SchoolDetailController', SchoolDetailController);

    SchoolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'School'];

    function SchoolDetailController($scope, $rootScope, $stateParams, previousState, entity, School) {
        var vm = this;

        vm.school = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleServiceApp:schoolUpdate', function(event, result) {
            vm.school = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
