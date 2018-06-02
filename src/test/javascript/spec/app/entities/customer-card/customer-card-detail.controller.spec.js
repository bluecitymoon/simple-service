'use strict';

describe('Controller Tests', function() {

    describe('CustomerCard Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerCard, MockCustomer, MockCustomerCardType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerCard = jasmine.createSpy('MockCustomerCard');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockCustomerCardType = jasmine.createSpy('MockCustomerCardType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerCard': MockCustomerCard,
                'Customer': MockCustomer,
                'CustomerCardType': MockCustomerCardType
            };
            createController = function() {
                $injector.get('$controller')("CustomerCardDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:customerCardUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
