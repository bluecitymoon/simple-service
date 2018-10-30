'use strict';

describe('Controller Tests', function() {

    describe('AuthorityUserGuideDocument Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAuthorityUserGuideDocument, MockAuthority, MockUserGuideDocument;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAuthorityUserGuideDocument = jasmine.createSpy('MockAuthorityUserGuideDocument');
            MockAuthority = jasmine.createSpy('MockAuthority');
            MockUserGuideDocument = jasmine.createSpy('MockUserGuideDocument');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AuthorityUserGuideDocument': MockAuthorityUserGuideDocument,
                'Authority': MockAuthority,
                'UserGuideDocument': MockUserGuideDocument
            };
            createController = function() {
                $injector.get('$controller')("AuthorityUserGuideDocumentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'simpleServiceApp:authorityUserGuideDocumentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
