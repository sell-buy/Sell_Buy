$(document).ready(function () {
    $('#register').submit(function (event) {
        event.preventDefault();
        let formData = {
            memId : $('#id').val(),
            password : $('#password').val(),
            name : $('#name').val(),
            phone : $('#phone').val(),
            email : $('#email').val(),
            nickname : $('#nickname').val()
        };
        $.ajax({
            type: 'POST',
            url: 'member/register',
            contentType: 'application/json',
            data: JSON.stringify(formData)
        });

    });
});