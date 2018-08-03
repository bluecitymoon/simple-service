(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('ContractPackageDetailController', ContractPackageDetailController);

    ContractPackageDetailController.$inject = ['$uibModal', '$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContractPackage', 'ContractTemplate', 'CustomerCardType', 'AlertService'];

    function ContractPackageDetailController($uibModal, $scope, $rootScope, $stateParams, previousState, entity, ContractPackage, ContractTemplate, CustomerCardType, AlertService) {
        var vm = this;

        vm.contractPackage = entity;
        vm.previousState = previousState.name;

        loadAllTemplates();

        function loadAllTemplates () {
            ContractTemplate.getContractTemplatesByPackageId({id: vm.contractPackage.id}
            , onSuccess, onError);

            function onSuccess(data) {
                vm.contractTemplates = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.openEditTemplateDialog = function (contractTemplate) {
            $uibModal.open({
                templateUrl: 'app/entities/contract-template/contract-template-dialog.html',
                controller: 'ContractTemplateDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: ['ContractTemplate', function(ContractTemplate) {
                        return ContractTemplate.get({id : contractTemplate.id}).$promise;
                    }]
                }
            });
        };

        vm.openDeleteTemplateDialog = function (contractTemplate) {
            $uibModal.open({
                templateUrl: 'app/entities/contract-template/contract-template-delete-dialog.html',
                controller: 'ContractTemplateDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: ['ContractTemplate', function(ContractTemplate) {
                        return ContractTemplate.get({id : contractTemplate.id}).$promise;
                    }]
                }
            }).result.then(function() {
                loadAllTemplates();
            }, function() {

            });
        };

        vm.openAddTemplateDialog = function () {

            $uibModal.open({
                templateUrl: 'app/entities/contract-template/contract-template-dialog.html',
                controller: 'ContractTemplateDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            totalMoneyAmount: null,
                            classCount: null,
                            totalMinutes: null,
                            totalHours: null,
                            years: null,
                            promotionAmount: null,
                            createdBy: null,
                            createdDate: null,
                            lastModifiedBy: null,
                            lastModifiedDate: null,
                            name: null,
                            id: null,
                            contractPackage: vm.contractPackage

                        };
                    }
                }
            }).result.then(function() {
                loadAllTemplates();
            }, function() {
            });
        };

        var unsubscribeloadAllTemplates = $rootScope.$on('simpleServiceApp:contractTemplateUpdate', function(event, result) {
            loadAllTemplates();
        });
        var unsubscribe = $rootScope.$on('simpleServiceApp:contractPackageUpdate', function(event, result) {
            vm.contractPackage = result;
        });

        $scope.$on('$destroy', unsubscribe);
        $scope.$on('$destroy', unsubscribeloadAllTemplates);
    }
})();
