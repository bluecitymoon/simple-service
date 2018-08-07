'use strict';

describe('Controller Tests', function() {

    describe('CustomerCollectionLog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomerCollectionLog, MockCollection, MockCustomer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomerCollectionLog = jasmine.createSpy('MockCustomerCollectionLog');
            MockCollection = jasmine.createSpy('MockCollection');
            MockCustomer = jasmine.createSpy('MockCustomer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomerCollectionLog': MockCustomerCollectionLog,
                'Collection': MockCollection,
                'Customer': MockCustomer
            };
            createController = function() {
                $injector.get('$controller')("CustomerCollectionLogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:customerCollectionLogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
