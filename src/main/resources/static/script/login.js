$(document).ready(function () {
    $('#login').submit(function (event) {
        event.preventDefault();
        let formData = {
            memId : $('#id').val(),
            password : $('#password').val(),
        };
        $.ajax({
            type: 'POST',
            url: 'member/login',
            contentType: 'application/json',
            data: JSON.stringify(formData)
        });
    });
});