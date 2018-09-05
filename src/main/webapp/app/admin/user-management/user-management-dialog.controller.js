(function() {
    'use strict';

    angular
        .module('simpleServiceApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', 'JhiLanguageService'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, JhiLanguageService) {
        var vm = this;

        // vm.authorities = [
        //     {code: "ROLE_USER", label: "MIS用户"},
        //     {code: "ROLE_ADMIN", label: "管理员"},
        //     {code: "ROLE_HEADMASTER", label: "校长"},
        //     {code: "ROLE_SALES", label: "销售经理"},
        //     {code: "ROLE_COURSE_CONSULTANT", label: "课程顾问"},
        //     {code: "ROLE_RECEPTION", label: "前台"},
        //     {code: "ROLE_PWI", label: "地推人员"},
        //     {code: "ROLE_FINANCE", label: "财务"}
        //     ];
        vm.authorities =
            ["ROLE_USER",
            "ROLE_ADMIN",
            "ROLE_HEADMASTER",
            "ROLE_SALES",
            "ROLE_SALES_MANAGER",
            "ROLE_COURSE_CONSULTANT",
            "ROLE_RECEPTION",
            "ROLE_PWI",
            "ROLE_FINANCE",
            "ROLE_EDUCATION_ADMIN"];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;


        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
