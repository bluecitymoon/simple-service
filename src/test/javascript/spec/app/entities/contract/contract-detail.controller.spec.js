'use strict';

describe('Controller Tests', function() {

    describe('Contract Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContract, MockStudent, MockCourse, MockContractStatus, MockProduct, MockCustomerCard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContract = jasmine.createSpy('MockContract');
            MockStudent = jasmine.createSpy('MockStudent');
            MockCourse = jasmine.createSpy('MockCourse');
            MockContractStatus = jasmine.createSpy('MockContractStatus');
            MockProduct = jasmine.createSpy('MockProduct');
            MockCustomerCard = jasmine.createSpy('MockCustomerCard');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Contract': MockContract,
                'Student': MockStudent,
                'Course': MockCourse,
                'ContractStatus': MockContractStatus,
                'Product': MockProduct,
                'CustomerCard': MockCustomerCard
            };
            createController = function() {
                $injector.get('$controller')("ContractDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:contractUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
