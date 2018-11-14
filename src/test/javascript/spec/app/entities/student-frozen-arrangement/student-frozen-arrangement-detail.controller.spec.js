'use strict';

describe('Controller Tests', function() {

    describe('StudentFrozenArrangement Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudentFrozenArrangement, MockStudent, MockClassArrangement, MockStudentFrozen;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudentFrozenArrangement = jasmine.createSpy('MockStudentFrozenArrangement');
            MockStudent = jasmine.createSpy('MockStudent');
            MockClassArrangement = jasmine.createSpy('MockClassArrangement');
            MockStudentFrozen = jasmine.createSpy('MockStudentFrozen');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'StudentFrozenArrangement': MockStudentFrozenArrangement,
                'Student': MockStudent,
                'ClassArrangement': MockClassArrangement,
                'StudentFrozen': MockStudentFrozen
            };
            createController = function() {
                $injector.get('$controller')("StudentFrozenArrangementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:studentFrozenArrangementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
