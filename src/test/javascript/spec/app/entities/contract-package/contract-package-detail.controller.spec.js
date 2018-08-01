'use strict';

describe('Controller Tests', function() {

    describe('ContractPackage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContractPackage, MockContractTemplate, MockCustomerCardType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContractPackage = jasmine.createSpy('MockContractPackage');
            MockContractTemplate = jasmine.createSpy('MockContractTemplate');
            MockCustomerCardType = jasmine.createSpy('MockCustomerCardType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ContractPackage': MockContractPackage,
                'ContractTemplate': MockContractTemplate,
                'CustomerCardType': MockCustomerCardType
            };
            createController = function() {
                $injector.get('$controller')("ContractPackageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:contractPackageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
