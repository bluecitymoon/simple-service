'use strict';

describe('Controller Tests', function() {

    describe('TimetableItem Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTimetableItem, MockProduct, MockTimeSegment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTimetableItem = jasmine.createSpy('MockTimetableItem');
            MockProduct = jasmine.createSpy('MockProduct');
            MockTimeSegment = jasmine.createSpy('MockTimeSegment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TimetableItem': MockTimetableItem,
                'Product': MockProduct,
                'TimeSegment': MockTimeSegment
            };
            createController = function() {
                $injector.get('$controller')("TimetableItemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:timetableItemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
