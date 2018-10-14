'use strict';

describe('Controller Tests', function() {

    describe('CustomerCardUpgradeLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerCardUpgradeLog, MockCustomerCardType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerCardUpgradeLog = jasmine.createSpy('MockCustomerCardUpgradeLog');
            MockCustomerCardType = jasmine.createSpy('MockCustomerCardType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerCardUpgradeLog': MockCustomerCardUpgradeLog,
                'CustomerCardType': MockCustomerCardType
            };
            createController = function() {
                $injector.get('$controller')("CustomerCardUpgradeLogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:customerCardUpgradeLogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
