(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('RegionDetailController', RegionDetailController);

    RegionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Region', 'User', 'UserRegion', 'AlertService'];

    function RegionDetailController($scope, $rootScope, $stateParams, previousState, entity, Region, User, UserRegion, AlertService) {
        var vm = this;

        vm.region = entity;
        vm.previousState = previousState.name;
        vm.users = User.query({size: 1000, page: 0});

        vm.assignToRegion = function () {

            var userRegion = {
                user: vm.selectedUser,
                region: vm.region
            };

            UserRegion.save(userRegion, function (response) {
                loadAllUsersInRegion();

            }, function (error) {
                AlertService.showCommonError(error);
            });
        };

        function loadAllUsersInRegion() {

            UserRegion.getAllUsersInRegion({regionId: vm.region.id}, function (response) {
                vm.assignedUsers = response;
            }, function (data) {

            });
        }

        loadAllUsersInRegion();

        vm.removeUserFromRegion = function (user) {

            var request = {
                userId: user.id,
                regionId: vm.region.id
            };
            UserRegion.removeUserFromRegion(request, function (response) {

                console.log(response);
                loadAllUsersInRegion();
            }, function (error) {
                AlertService.showCommonError(error);
            });
        };

        var unsubscribe = $rootScope.$on('simpleServiceApp:regionUpdate', function(event, result) {
            vm.region = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
