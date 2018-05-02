'use strict';

describe('Controller Tests', function() {

    describe('CustomerCommunicationLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerCommunicationLog, MockCustomerCommunicationLogType, MockCustomer, MockFreeClassRecord;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerCommunicationLog = jasmine.createSpy('MockCustomerCommunicationLog');
            MockCustomerCommunicationLogType = jasmine.createSpy('MockCustomerCommunicationLogType');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockFreeClassRecord = jasmine.createSpy('MockFreeClassRecord');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerCommunicationLog': MockCustomerCommunicationLog,
                'CustomerCommunicationLogType': MockCustomerCommunicationLogType,
                'Customer': MockCustomer,
                'FreeClassRecord': MockFreeClassRecord
            };
            createController = function() {
                $injector.get('$controller')("CustomerCommunicationLogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:customerCommunicationLogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
