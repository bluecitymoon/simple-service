'use strict';

describe('Controller Tests', function() {

    describe('StudentAbsenceLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudentAbsenceLog, MockStudent, MockClassArrangement;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudentAbsenceLog = jasmine.createSpy('MockStudentAbsenceLog');
            MockStudent = jasmine.createSpy('MockStudent');
            MockClassArrangement = jasmine.createSpy('MockClassArrangement');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StudentAbsenceLog': MockStudentAbsenceLog,
                'Student': MockStudent,
                'ClassArrangement': MockClassArrangement
            };
            createController = function() {
                $injector.get('$controller')("StudentAbsenceLogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:studentAbsenceLogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
