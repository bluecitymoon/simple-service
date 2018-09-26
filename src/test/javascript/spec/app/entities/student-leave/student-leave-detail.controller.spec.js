'use strict';

describe('Controller Tests', function() {

    describe('StudentLeave Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudentLeave, MockStudent, MockClassArrangement;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudentLeave = jasmine.createSpy('MockStudentLeave');
            MockStudent = jasmine.createSpy('MockStudent');
            MockClassArrangement = jasmine.createSpy('MockClassArrangement');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StudentLeave': MockStudentLeave,
                'Student': MockStudent,
                'ClassArrangement': MockClassArrangement
            };
            createController = function() {
                $injector.get('$controller')("StudentLeaveDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:studentLeaveUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
