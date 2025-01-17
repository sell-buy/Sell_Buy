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

// 이미지 업로드
function uploadImage(input, id) {
    const container = $("#" + id);
    const hiddenInput = $('#hidden-' + id); // 각 이미지에 대응하는 hidden input
    const file = input.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function (e) {
            // 이미지 미리보기 설정
            container.css('background-image', 'url(' + e.target.result + ')');

            // hidden input에 base64 이미지 데이터 설정
            hiddenInput.val(e.target.result);

            // 이미지 컨테이너에 상태 클래스 추가
            container.addClass('has-image');

            // 이미지 리스트 재정렬
            reorderImages();
        };

        reader.onerror = function (error) {
            console.error("FileReader Error: ", error);
        };

        reader.readAsDataURL(file);
    } else {
        console.error("No file selected.");
    }
}

// 이미지 삭제
function deleteImage(id) {
    const container = $("#" + id);
    const hiddenInput = $('#hidden-' + id);

    // 배경 이미지 및 hidden input 초기화
    container.css('background-image', '');
    hiddenInput.val('');

    // 이미지 컨테이너에 상태 클래스 제거
    container.removeClass('has-image');

    if ()

    // 이미지 리스트 재정렬
    reorderImages();
}

// 이미지 리스트 재정렬
function reorderImages() {
    const images = $('.img-wrapper'); // 모든 이미지 래퍼 선택
    const parent = $('.small-images'); // 부모 컨테이너

    // 이미지를 가진 요소를 앞쪽으로, 없는 요소를 뒤쪽으로 정렬
    images.sort(function (a, b) {
        const hasImageA = $(a).find('.img-insert').hasClass('has-image');
        const hasImageB = $(b).find('.img-insert').hasClass('has-image');
        return hasImageB - hasImageA; // 이미지가 있으면 앞으로
    });

    // 정렬된 순서대로 다시 추가
    images.each(function () {
        parent.append($(this));
    });
}


// 이미지 미리보기
function viewImage(id) {
    const container = $("#" + id);
    const bigPreview = $('#big-preview');
    const backgroundImage = container.css('background-image');


    // 이미지가 없으면 실행하지 않음
    if (!backgroundImage && backgroundImage === 'none') {

    } else {
        // 이미지가 있으면 이미지를 큰 이미지 미리보기에 설정
        bigPreview.css('background-image', backgroundImage);
    }
}

// 이벤트 핸들러를 jQuery 방식으로 설정
$(".img-upload").on('change', function () {
    const id = $(this).closest('.img-insert').attr('id');
    uploadImage(this, id);
});

$(".view-btn").on('click', function () {
    const id = $(this).closest('.img-wrapper').find('.img-insert').attr('id');
    viewImage(id);
});

$(".delete-btn").on('click', function () {
    const id = $(this).closest('.img-wrapper').find('.img-insert').attr('id');
    deleteImage(id);
});

