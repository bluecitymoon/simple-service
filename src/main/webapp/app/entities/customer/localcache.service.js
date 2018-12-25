(function () {
    'use strict';
    angular
        .module('simpleServiceApp')
        .factory('Cache', Cache);

    Cache.$inject = [];

    function Cache() {

        this.customerCondition = null;
        this.newOrderCondition = null;
        this.searchCondition = null;

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

        function setSearchCondition(searchCondition) {
            this.searchCondition = searchCondition;
        }

        function getSearchCondition() {
            return this.searchCondition;
        }
        return {
            getCustomerSearchCondition: getCustomerSearchCondition,
            setCustomerSearchCondition: setCustomerSearchCondition,
            getNewOrderCondition: getNewOrderCondition,
            setNewOrderCondition: setNewOrderCondition,
            setSearchCondition: setSearchCondition,
            getSearchCondition: getSearchCondition
        }
    }
})();
