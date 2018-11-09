'use strict';

describe('Controller Tests', function() {

    describe('ClassCategory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClassCategory, MockFreeClassRecord, MockClassCategoryBase;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClassCategory = jasmine.createSpy('MockClassCategory');
            MockFreeClassRecord = jasmine.createSpy('MockFreeClassRecord');
            MockClassCategoryBase = jasmine.createSpy('MockClassCategoryBase');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ClassCategory': MockClassCategory,
                'FreeClassRecord': MockFreeClassRecord,
                'ClassCategoryBase': MockClassCategoryBase
            };
            createController = function() {
                $injector.get('$controller')("ClassCategoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:classCategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
