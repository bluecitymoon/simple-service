(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('UserGuideDocumentDeleteController',UserGuideDocumentDeleteController);

    UserGuideDocumentDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserGuideDocument'];

    function UserGuideDocumentDeleteController($uibModalInstance, entity, UserGuideDocument) {
        var vm = this;

        vm.userGuideDocument = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserGuideDocument.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
