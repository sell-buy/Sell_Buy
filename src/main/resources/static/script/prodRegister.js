$(document).ready(function () {
    loadCategories();

    $('#prodRegisterForm').submit(function (event) {
        event.preventDefault();

        const directType = $('#type_direct').is(':checked');
        const deliveryType = $('#type_delivery').is(':checked');

        let tradeType = -1;
        if (directType && deliveryType) {
            tradeType = 2;
        } else if (directType || !deliveryType) {
            tradeType = 0;
        } else if (!directType || deliveryType) {
            tradeType = 1;
        }
        if (tradeType === -1) {
            alert('거래 방식을 선택해주세요.');
            return false;
        }
        const formData = new FormData();
        const product = {
            prodName: $('#prodName').val(),
            price: $('#price').val(),
            prodDesc: $('#prodDesc').val(),
            tradeType: tradeType,
            catId: $('#category').val()
        }

        formData.append('product', new Blob([JSON.stringify(product)],
            {type: 'application/json'})
        );

        const imgFiles = $('#images').files;
        if (imgFiles.length > 4) {
            alert('이미지는 최대 4개까지 업로드 가능합니다.');
            return false;
        }

        for (const file of imgFiles) {
            formData.append('images', file);
        }

        $.ajax({
            type: 'POST',
            url: '/prod/register',
            data: formData,
            processData: false,
            contentType: false,
            success: function (xhr) {
                window.location.href = '/prod/' + xhr;
            },
            error: function (xhr) {
                alert('상품 등록에 실패했습니다.');
                console.error(xhr);
            }
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
                            .on('click', function () {
                                $(this).siblings().removeClass('selected'); // 같은 레벨의 항목에서 selected 클래스 제거
                                $(this).addClass('selected'); // 클릭된 항목에 selected 클래스 추가
                                loadCategories(category.catId);
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
});
