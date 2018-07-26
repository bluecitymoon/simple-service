'use strict';

describe('Controller Tests', function() {

    describe('ContractTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContractTemplate, MockCustomerCardType, MockContractNature;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContractTemplate = jasmine.createSpy('MockContractTemplate');
            MockCustomerCardType = jasmine.createSpy('MockCustomerCardType');
            MockContractNature = jasmine.createSpy('MockContractNature');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ContractTemplate': MockContractTemplate,
                'CustomerCardType': MockCustomerCardType,
                'ContractNature': MockContractNature
            };
            createController = function() {
                $injector.get('$controller')("ContractTemplateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:contractTemplateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
