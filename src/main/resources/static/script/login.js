$(document).ready(function () {
    $('#login-button').click(function () {
        $('#login').submit();
    });
    /*    $('#login').submit(function (event) {
            event.preventDefault();
            let formData = {
                loginId: $('#loginId').val(),
                password: $('#password').val(),
            };
            $.ajax({
                type: 'POST',
                url: 'member/login',
                contentType: 'application/json',
                data: JSON.stringify(formData)
            });
        });*/
});