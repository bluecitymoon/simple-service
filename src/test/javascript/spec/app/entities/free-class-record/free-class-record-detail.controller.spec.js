'use strict';

describe('Controller Tests', function() {

    describe('FreeClassRecord Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFreeClassRecord, MockMarketChannelCategory, MockClassCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFreeClassRecord = jasmine.createSpy('MockFreeClassRecord');
            MockMarketChannelCategory = jasmine.createSpy('MockMarketChannelCategory');
            MockClassCategory = jasmine.createSpy('MockClassCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'FreeClassRecord': MockFreeClassRecord,
                'MarketChannelCategory': MockMarketChannelCategory,
                'ClassCategory': MockClassCategory
            };
            createController = function() {
                $injector.get('$controller')("FreeClassRecordDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:freeClassRecordUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
