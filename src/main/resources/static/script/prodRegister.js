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
            url: '<c:url value="/prod"/>',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function () {
                alert('등록되었습니다.');
                location.href = '/prod/list';
            },
            error: function () {
                alert('등록에 실패했습니다.');
            }
        });
    });
    loadCategories();
});

function loadCategories(catId) {
    if (catId === undefined) {
        $.get('/categories')
            .done(function (data) {
                let categoryNav = $('#category-dep1');
                categoryNav.empty(); // Clear any existing content

                // Sort categories alphabetically by name
                data.sort(function (a, b) {
                    return a.name.localeCompare(b.name);
                });

                data.forEach(function (category) {
                    let categoryItem = $('<ll>').addClass('category-item');
                    let categoryBtn = $('<button>').addClass('category-btn').attr('type', 'button')
                        .on('click', function () {
                            loadCategories(category.catId);
                        });
                    let categoryName = $('<p>').text(category.name);
                    categoryItem.append(categoryBtn);
                    categoryBtn.append(categoryName);
                    categoryNav.append(categoryItem);
                });
            });
    } else {
        $.get('/categories', {catId: catId})
            .done(function (data) {
                let categoryNav;
                if (catId < 100) {
                    categoryNav = $('#category-dep2');
                } else if (catId < 10000) {
                    categoryNav = $('#category-dep3');
                }
                categoryNav.empty(); // Clear any existing content

                // Sort categories alphabetically by name
                data.sort(function (a, b) {
                    return a.name.localeCompare(b.name);
                });

                data.forEach(function (category) {
                    let categoryItem = $('<ll>').addClass('category-item');
                    let categoryBtn
                    if (!(categoryNav === $('#category-dep3'))) {
                        categoryBtn = $('<button>').addClass('category-btn').attr('type', 'button')
                            .on('click', function () {
                                loadCategories(category.catId);
                            });
                    } else {
                        categoryBtn = $('<button>').addClass('category-btn').attr('type', 'button')
                            .on('click', function () {
                                $('#category').val(category.catId);
                            });
                    }
                    let categoryName = $('<p>').text(category.name);
                    categoryItem.append(categoryBtn);
                    categoryBtn.append(categoryName);
                    categoryNav.append(categoryItem);

                });
            });
    }
}

/*

document.addEventListener('DOMContentLoaded', function () {
    const header_search = document.querySelector('.header-search');
    const category = document.querySelector('#category');
    let isAdded = false;
    //카테고리 클릭시 컨트롤러에서 json타입으로 값 리턴한거 계층 구조로 표시
    category.addEventListener('click', function () {
        if (!isAdded) {
            const options = [
                new Option("category_name", "category_id"),
                new Option("category_name", "category_id"),
                new Option("category_name", "category_id"),
                new Option("category_name", "category_id"),
            ]
            isAdded = true
            options.forEach(option => {
                this.add(option);
            })

        }
    })
    // 가격입력
    document.getElementById('price').addEventListener('input', function () {
        if (isNaN(this.value)) {
            alert('숫자만 입력 가능합니다.');
            this.value = this.value.replace(/[^0-9]/g, '');
        }
    });
});
*/
