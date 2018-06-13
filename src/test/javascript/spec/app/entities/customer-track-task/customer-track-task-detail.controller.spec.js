'use strict';

describe('Controller Tests', function() {

    describe('CustomerTrackTask Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerTrackTask, MockCustomer, MockTask;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerTrackTask = jasmine.createSpy('MockCustomerTrackTask');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockTask = jasmine.createSpy('MockTask');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerTrackTask': MockCustomerTrackTask,
                'Customer': MockCustomer,
                'Task': MockTask
            };
            createController = function() {
                $injector.get('$controller')("CustomerTrackTaskDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:customerTrackTaskUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
