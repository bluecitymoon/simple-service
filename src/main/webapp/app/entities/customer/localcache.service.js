(function () {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('Cache', Cache);

    Cache.$inject = [];

    function Cache() {

        this.customerCondition = null;
        this.newOrderCondition = null;

        function getCustomerSearchCondition() {
            return this.customerCondition;
        }

        function setCustomerSearchCondition(condition) {
            this.customerCondition = condition;
        }

        function getNewOrderCondition() {
            return this.newOrderCondition;
        }

        function setNewOrderCondition(condition) {
            this.newOrderCondition = condition;
        }
        return {
            getCustomerSearchCondition: getCustomerSearchCondition,
            setCustomerSearchCondition: setCustomerSearchCondition,
            getNewOrderCondition: getNewOrderCondition,
            setNewOrderCondition: setNewOrderCondition
        }
    }
})();
