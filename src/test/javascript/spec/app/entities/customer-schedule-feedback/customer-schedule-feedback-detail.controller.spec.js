'use strict';

describe('Controller Tests', function() {

    describe('CustomerScheduleFeedback Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerScheduleFeedback, MockCustomer, MockCustomerCommunicationSchedule;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerScheduleFeedback = jasmine.createSpy('MockCustomerScheduleFeedback');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockCustomerCommunicationSchedule = jasmine.createSpy('MockCustomerCommunicationSchedule');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerScheduleFeedback': MockCustomerScheduleFeedback,
                'Customer': MockCustomer,
                'CustomerCommunicationSchedule': MockCustomerCommunicationSchedule
            };
            createController = function() {
                $injector.get('$controller')("CustomerScheduleFeedbackDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:customerScheduleFeedbackUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
