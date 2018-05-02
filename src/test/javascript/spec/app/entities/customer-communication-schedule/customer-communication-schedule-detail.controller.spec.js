'use strict';

describe('Controller Tests', function() {

    describe('CustomerCommunicationSchedule Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerCommunicationSchedule, MockCustomer, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerCommunicationSchedule = jasmine.createSpy('MockCustomerCommunicationSchedule');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerCommunicationSchedule': MockCustomerCommunicationSchedule,
                'Customer': MockCustomer,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("CustomerCommunicationScheduleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:customerCommunicationScheduleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
