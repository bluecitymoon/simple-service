(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerSigninController',CustomerSigninController);

    CustomerSigninController.$inject = ['$uibModalInstance', 'CustomerCommunicationSchedule', '$stateParams'];

    function CustomerSigninController($uibModalInstance, CustomerCommunicationSchedule, $stateParams) {
        var vm = this;

        vm.clear = clear;
        vm.confirmSignin = confirmSignin;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmSignin () {

            CustomerCommunicationSchedule.sign({id: $stateParams.cid},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
