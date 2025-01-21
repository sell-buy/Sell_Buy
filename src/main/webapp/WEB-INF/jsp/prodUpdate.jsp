<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sell&Buy :: Product Register</title>

    <script src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <script src="<c:url value="/webjars/jquery-ui/1.14.1/jquery-ui.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/webjars/jquery-ui/1.14.1/jquery-ui.css"/>">
    <script src="<c:url value="/webjars/jquery-validation/1.20.0/jquery.validate.js"/>"></script>

    <link rel="stylesheet" href="<c:url value="/style/common.css"/>">
    <link rel="stylesheet" href="<c:url value="/style/prodRegister.css"/>">

    <script src="<c:url value="/script/prodRegisterValidator.js"/>"></script>
    <script src="<c:url value="/script/prodRegister.js"/>"></script>
    <script src="<c:url value="/script/category.js"/>"></script>

    <script>

        function preselectCategories(catId, isCategoryLoaded) {
            if (catId === undefined) return;

            $.get('http://localhost/category/super?catId=' + catId)
                .done(function (data) {
                    const dep1 = data.dep1[0];
                    const dep2 = data.dep2[0];

                    console.log(dep1, dep2, catId);

                    if (isCategoryLoaded) {
                        handleCategoryLoaded();
                    } else {
                        document.addEventListener('categoriesLoaded', handleCategoryLoaded);
                    }

                    function handleCategoryLoaded() {
                        console.log('categoriesLoaded event detected!');


                        // dep1 선택
                        $('.category-dep1 li').filter(function () {
                            console.log($(this).attr('catid') === dep1);
                            return $(this).attr('catId') === dep1;
                        }).trigger('click');

                        // dep2 선택
                        $('.category-dep2 li').filter(function () {
                            return $(this).attr('catId') === dep2;
                        }).trigger('click');

                        // dep3 선택
                        $('.category-dep3 li').filter(function () {
                            return $(this).attr('catId') === catId;
                        }).trigger('click');
                    }
                })
                .fail(() => console.error('Failed to fetch category data.'));
        }

        $(function () {
            let prodType = ${product.prodType};
            if (prodType === 0) {
                $('#type_direct').prop('checked', true);
            } else if (prodType === 1) {
                $('#type_delivery').prop('checked', true);
            } else {
                $('#type_direct').prop('checked', true);
                $('#type_delivery').prop('checked', true);
            }
            const productCatId = ${product.category};
            console.log(productCatId);
            let isCategoryLoaded = false;
            document.addEventListener('categoriesLoaded', () => {
                isCategoryLoaded = true;
            });

            preselectCategories(productCatId);
        });


    </script>
</head>
<body>
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="content">
        <div class="main-container">
            <div class="content-left">
                <form id="prodUpdateForm" class="update-form">
                    <input type="hidden" id="category">
                    <label for="prodName">
                        <input type="text" id="prodName" name="prodName" placeholder="상품 이름" required
                               value="${product.prodName}">
                    </label>
                    <label for="price">
                        <input type="text" id="price" name="price" placeholder="상품 가격" required
                               value="${product.price}">
                    </label>
                    <div class="prodType">
                        <input type="checkbox" id="type_direct" name="trade_type" value="0">
                        <label for="type_direct">직거래</label>
                        <input type="checkbox" id="type_delivery" name="trade_type" value="1">
                        <label for="type_delivery">택배거래</label>
                    </div>
                    <label for="prodDesc">
                        <textarea id="prodDesc" name="prodDesc" placeholder="상품 설명" class="custom-scrollbar"
                                  required>${product.prodDesc}</textarea>
                    </label>
                    <button type="submit">등록하기</button>
                </form>
            </div>
            <div class="content-center">
                <div class="category-dep1 custom-scrollbar">
                    <ul id="category-dep1"></ul>
                </div>
                <div class="category-dep2 custom-scrollbar">
                    <ul id="category-dep2"></ul>
                </div>
                <div class="category-dep3 custom-scrollbar">
                    <ul id="category-dep3"></ul>
                </div>
            </div>

            <div class="content-right">
                <div class="image-container">
                    <!-- 큰 이미지 -->
                    <div id="big-preview" class="big-image" style="background-image: url(${imageUrls[0]})"></div>

                    <!-- 작은 이미지 -->
                    <div class="small-images">
                        <!-- 이미지 1 -->
                        <div class="img-card">
                            <div class="img-insert" id="img1" style="background-image: url(${imageUrls[0]})">
                                <input type="hidden" id="hidden-img1" name="image1">
                            </div>
                            <div class="img-controls">
                                <button class="view-btn" onclick="viewImage('img1')">보기</button>
                            </div>
                        </div>

                        <!-- 이미지 2 -->
                        <div class="img-card">
                            <div class="img-insert" id="img2" style="background-image: url(${imageUrls[1]})">
                                <input type="hidden" id="hidden-img2" name="image2">
                            </div>
                            <div class="img-controls">
                                <button class="view-btn" onclick="viewImage('img2')">보기</button>
                            </div>
                        </div>

                        <!-- 이미지 3 -->
                        <div class="img-card">
                            <div class="img-insert" id="img3" style="background-image: url(${imageUrls[2]})">
                                <input type="hidden" id="hidden-img3" name="image3">
                            </div>
                            <div class="img-controls">
                                <button class="view-btn" onclick="viewImage('img3')">보기</button>
                            </div>
                        </div>

                        <!-- 이미지 4 -->
                        <div class="img-card">
                            <div class="img-insert" id="img4" style="background-image: url(${imageUrls[3]})">
                                <input type="hidden" id="hidden-img4" name="image4">
                            </div>
                            <div class="img-controls">
                                <button class="view-btn" onclick="viewImage('img4')">보기</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</div>

</body>
</html>
