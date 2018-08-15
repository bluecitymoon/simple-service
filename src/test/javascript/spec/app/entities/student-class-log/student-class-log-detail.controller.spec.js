'use strict';

describe('Controller Tests', function() {

    describe('StudentClassLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudentClassLog, MockStudent, MockClassArrangement;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudentClassLog = jasmine.createSpy('MockStudentClassLog');
            MockStudent = jasmine.createSpy('MockStudent');
            MockClassArrangement = jasmine.createSpy('MockClassArrangement');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StudentClassLog': MockStudentClassLog,
                'Student': MockStudent,
                'ClassArrangement': MockClassArrangement
            };
            createController = function() {
                $injector.get('$controller')("StudentClassLogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:studentClassLogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
