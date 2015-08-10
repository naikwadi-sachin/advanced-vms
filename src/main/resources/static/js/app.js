(function () {
    var app = angular.module('vms', []);
    console.log('created angular module');

    app.controller('LoginController', function () {
        this.login = {};
        this.validateLogin = function () {
            console.log(JSON.stringify(this.login));

            var login = this.login;
            $.ajax({
                url: '/checkUser',
                data: JSON.stringify(login),
                method: 'POST',
                contentType: 'application/json',
                success: function (data) {
                    console.log("result data=" + data);
                },
                error: function (xhr, ajaxOptions, throwError) {
                    console.log("error=" + throwError.message);
                }
            });
        };
    });
})();

