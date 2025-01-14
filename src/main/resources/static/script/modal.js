var loginPageCache = null;
var registerPageCache = null;

$(document).ready(function () {
    $('#href-open-login').on('click', function (event) {
        event.preventDefault();
        if (loginPageCache) {
            $('#modalContainer').html(loginPageCache);
            $('.modal').css('display', 'flex');
        } else {
            $.get('/member/login', function (data) {
                loginPageCache = data;
                $('#modalContainer').html(data);
                $('.modal').css('display', 'flex');
            });
        }
    });

    $('#href-open-login-header').on('click', function (event) {
        event.preventDefault();
        if (loginPageCache) {
            $('#modalContainer').html(loginPageCache);
            $('.modal').css('display', 'flex');
        } else {
            $.get('/member/login', function (data) {
                loginPageCache = data;
                $('#modalContainer').html(data);
                $('.modal').css('display', 'flex');
            });
        }
    });

    $('#href-open-register').on('click', function (event) {
        event.preventDefault();
        if (registerPageCache) {
            $('#modalContainer').html(registerPageCache);
            $('.modal').css('display', 'flex');
        } else {
            $.get('/member/register', function (data) {
                registerPageCache = data;
                $('#modalContainer').html(data);
                $('.modal').css('display', 'flex');
            });
        }
    });

    $(document).on('click', function (event) {
        if (!$(event.target).closest('.modal_body').length && !$(event.target).closest('#href-open-login').length && !$(event.target).closest('#href-open-register').length) {
            $('.modal').css('display', 'none');
        }
    });

    $(document).on('click', '.modal_body', function (event) {
        event.stopPropagation();
    });
});