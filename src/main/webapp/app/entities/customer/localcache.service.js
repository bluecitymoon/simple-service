(function() {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('Cache', Cache);

    Cache.$inject = [];

    function Cache () {

         this.customerCondition = null;

        function getCustomerSearchCondition() {
            return this.customerCondition;
        }

        function setCustomerSearchCondition(condition) {
            this.customerCondition = condition;
        }
        return {
            getCustomerSearchCondition: getCustomerSearchCondition,
            setCustomerSearchCondition: setCustomerSearchCondition
        }
    }
})();
