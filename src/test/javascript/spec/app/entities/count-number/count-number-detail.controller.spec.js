'use strict';

describe('Controller Tests', function() {

    describe('CountNumber Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCountNumber, MockClassArrangementRuleLoopWay;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCountNumber = jasmine.createSpy('MockCountNumber');
            MockClassArrangementRuleLoopWay = jasmine.createSpy('MockClassArrangementRuleLoopWay');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CountNumber': MockCountNumber,
                'ClassArrangementRuleLoopWay': MockClassArrangementRuleLoopWay
            };
            createController = function() {
                $injector.get('$controller')("CountNumberDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:countNumberUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
