$(function () {
    $.get("/categories")
        .done(function (data) {
            let categoryNav = $('.category');
            categoryNav.empty(); // Clear any existing content

            // Sort categories alphabetically by name
            data.sort(function (a, b) {
                return a.name.localeCompare(b.name);
            });

            data.forEach(function (category) {
                let categoryItem = $('<div>').addClass('category-item');
                let categoryLink = $('<a>').attr('href', 'http://localhost/prod/list?catId=' + category.catId).text(category.name);
                categoryItem.append(categoryLink);
                categoryNav.append(categoryItem);
            });
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.error("Error fetching categories:", textStatus, errorThrown);
        });
});

function loadCategories(catId) {
    if (catId === undefined) {
        $.get('/categories')
            .done(function (data) {
                let categoryNav = $('#category-dep1');
                categoryNav.empty(); // 기존 내용 삭제

                // 카테고리를 이름순으로 정렬
                data.sort(function (a, b) {
                    return a.name.localeCompare(b.name);
                });

                data.forEach(function (category) {
                    let categoryItem = $('<li>').addClass('category-item')
                        .attr('catId', category.catId)
                        .on('click', function () {
                            $(this).siblings().removeClass('selected'); // 같은 레벨의 항목에서 selected 클래스 제거
                            $(this).addClass('selected'); // 클릭된 항목에 selected 클래스 추가
                            loadCategories(category.catId); // 하위 카테고리 로드
                            $('#category-dep2').empty(); // 하위 카테고리 삭제
                            $('#category-dep3').empty(); // 하위 카테고리 삭제
                            $('#category').val(''); // hidden input 초기화
                        });

                    let categoryBtn = $('<button>').addClass('category-btn').attr('type', 'button');
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
                    $('#category-dep3').empty(); // 하위 카테고리 삭제
                    $('#category').val(''); // hidden input 초기화
                } else if (catId < 10000) {
                    categoryNav = $('#category-dep3');
                }
                categoryNav.empty(); // 기존 내용 삭제

                // 카테고리를 이름순으로 정렬
                data.sort(function (a, b) {
                    return a.name.localeCompare(b.name);
                });

                data.forEach(function (category) {
                    let categoryItem = $('<li>').addClass('category-item')
                        .attr('catId', category.catId)
                        .on('click', function () {
                            $(this).siblings().removeClass('selected'); // 같은 레벨의 항목에서 selected 클래스 제거
                            $(this).addClass('selected'); // 클릭된 항목에 selected 클래스 추가

                            if (categoryNav.is($('#category-dep3'))) {
                                $('#category').val(category.catId); // hidden input에 하위 카테고리 ID 설정
                            } else {
                                loadCategories(category.catId);
                                $('#category').val(''); // hidden input 초기화
                            }
                        });

                    let categoryBtn = $('<button>').addClass('category-btn').attr('type', 'button');
                    let categoryName = $('<p>').text(category.name);
                    categoryItem.append(categoryBtn);
                    categoryBtn.append(categoryName);
                    categoryNav.append(categoryItem);
                });
            });
    }
}