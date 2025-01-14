$(document).ready(function () {
    $('#register-button').click(function () {
        $('#register').submit();
    })
    $('#register').submit(function (event) {
        event.preventDefault();
        let formData = {
            loginId: $('#loginId').val(),
            password: $('#password').val(),
            name: $('#name').val(),
            phoneNum: $('#phone').val(),
            email: $('#email').val(),
            nickname: $('#nickname').val()
        };
        $.ajax({
            type: 'POST',
            url: 'member/register',
            contentType: 'application/json',
            data: JSON.stringify(formData)
        });

    });
});