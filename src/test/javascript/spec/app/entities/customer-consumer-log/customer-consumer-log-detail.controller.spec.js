'use strict';

describe('Controller Tests', function() {

    describe('CustomerConsumerLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerConsumerLog, MockCustomerConsumerType, MockStudent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerConsumerLog = jasmine.createSpy('MockCustomerConsumerLog');
            MockCustomerConsumerType = jasmine.createSpy('MockCustomerConsumerType');
            MockStudent = jasmine.createSpy('MockStudent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerConsumerLog': MockCustomerConsumerLog,
                'CustomerConsumerType': MockCustomerConsumerType,
                'Student': MockStudent
            };
            createController = function() {
                $injector.get('$controller')("CustomerConsumerLogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:customerConsumerLogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
