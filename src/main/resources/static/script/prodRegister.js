$(document).ready(function () {
    $('#prodRegisterForm').submit(function (event) {
        event.preventDefault();
        let formData = {
            name: $('#name').val(),
            price: $('#price').val(),
            description: $('#description').val(),
            category: 1,
            is_auction: false,
            create_date: new Date().toLocaleString('sv-SE', {timeZone: 'Asia/Seoul'}).replace(' ', 'T') + '.' + String(new Date().getMilliseconds()).padStart(3, '0')
        };
        $.ajax({
            type: 'POST',
            url: '<c:url value="/prod/register"/>',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function () {
                alert('등록되었습니다.');
                location.href = '/prod';
            },
            error: function () {
                alert('등록에 실패했습니다.');
            }
        });
    });
});