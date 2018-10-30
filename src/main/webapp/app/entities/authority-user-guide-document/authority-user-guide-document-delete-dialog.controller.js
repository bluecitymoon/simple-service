(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('AuthorityUserGuideDocumentDeleteController',AuthorityUserGuideDocumentDeleteController);

    AuthorityUserGuideDocumentDeleteController.$inject = ['$uibModalInstance', 'entity', 'AuthorityUserGuideDocument'];

    function AuthorityUserGuideDocumentDeleteController($uibModalInstance, entity, AuthorityUserGuideDocument) {
        var vm = this;

        vm.authorityUserGuideDocument = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AuthorityUserGuideDocument.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
