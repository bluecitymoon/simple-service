(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('CustomerTrackTaskDialogController', CustomerTrackTaskDialogController);

    CustomerTrackTaskDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'CustomerTrackTask', 'Customer', 'Task'];

    function CustomerTrackTaskDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, CustomerTrackTask, Customer, Task) {
        var vm = this;

        vm.customerTrackTask = entity;
        vm.clear = clear;
        vm.save = save;
        vm.customers = Customer.query();
        vm.tasks = Task.query({filter: 'customertracktask-is-null'});
        $q.all([vm.customerTrackTask.$promise, vm.tasks.$promise]).then(function() {
            if (!vm.customerTrackTask.task || !vm.customerTrackTask.task.id) {
                return $q.reject();
            }
            return Task.get({id : vm.customerTrackTask.task.id}).$promise;
        }).then(function(task) {
            vm.tasks.push(task);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerTrackTask.id !== null) {
                CustomerTrackTask.update(vm.customerTrackTask, onSaveSuccess, onSaveError);
            } else {
                CustomerTrackTask.save(vm.customerTrackTask, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleServiceApp:customerTrackTaskUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
