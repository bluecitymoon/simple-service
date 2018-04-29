'use strict';

describe('Controller Tests', function() {

    describe('NewOrderWechatUserInfo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockNewOrderWechatUserInfo, MockFreeClassRecord;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockNewOrderWechatUserInfo = jasmine.createSpy('MockNewOrderWechatUserInfo');
            MockFreeClassRecord = jasmine.createSpy('MockFreeClassRecord');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'NewOrderWechatUserInfo': MockNewOrderWechatUserInfo,
                'FreeClassRecord': MockFreeClassRecord
            };
            createController = function() {
                $injector.get('$controller')("NewOrderWechatUserInfoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:newOrderWechatUserInfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
