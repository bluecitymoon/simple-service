'use strict';

describe('Controller Tests', function() {

    describe('ClassArrangement Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClassArrangement, MockTeacher, MockClassArrangementStatus;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClassArrangement = jasmine.createSpy('MockClassArrangement');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockClassArrangementStatus = jasmine.createSpy('MockClassArrangementStatus');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ClassArrangement': MockClassArrangement,
                'Teacher': MockTeacher,
                'ClassArrangementStatus': MockClassArrangementStatus
            };
            createController = function() {
                $injector.get('$controller')("ClassArrangementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:classArrangementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
