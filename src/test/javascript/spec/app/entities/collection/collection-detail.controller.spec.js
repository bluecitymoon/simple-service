'use strict';

describe('Controller Tests', function() {

    describe('Collection Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCollection, MockFinanceCategory, MockPaymentType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCollection = jasmine.createSpy('MockCollection');
            MockFinanceCategory = jasmine.createSpy('MockFinanceCategory');
            MockPaymentType = jasmine.createSpy('MockPaymentType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Collection': MockCollection,
                'FinanceCategory': MockFinanceCategory,
                'PaymentType': MockPaymentType
            };
            createController = function() {
                $injector.get('$controller')("CollectionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:collectionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
