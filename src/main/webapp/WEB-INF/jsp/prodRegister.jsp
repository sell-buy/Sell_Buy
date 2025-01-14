<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy</title>
    <script async src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/style/common.css"/>">
    <style>
        #wrap {
            width: 100%;
            height: 100%;
        }

        .main-container {
            min-height: 100%;
            min-width: 100%;
        }

        .content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .product-title {
            color: white;
            font-size: 24px;
            margin-bottom: 20px;
        }

        .product-layout {
            display: flex;
            gap: 30px;
            justify-content: space-between;
            width: 100%;
        }

        .category-list {
            width: 15%;
            color: white;
        }

        .category-item {
            padding: 10px;
            margin-bottom: 10px;
            cursor: pointer;
            background-color: rgba(255, 255, 255, 0.1);
            border-radius: 4px;
        }

        .image-section {
            width: 60%;
            display: flex;
            gap: 20px;
        }

        .thumbnail-list {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .thumbnail {
            width: 100px;
            height: 100px;
            background-color: #666;
            cursor: pointer;
            transition: all 0.3s ease;
            border-radius: 4px;
        }

        .main-image {
            width: 100%;
            aspect-ratio: 4/3;
            background-color: #666;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            transition: all 0.3s ease;
            border-radius: 4px;
        }

        .product-info {
            width: 20%;
            color: white;
        }

        .price {
            font-size: 20px;
            margin: 20px 0;
        }

        .delivery-options {
            display: flex;
            gap: 20px;
            margin: 20px 0;
        }

        .product-description {
            width: 100%;
            height: 200px;
            background-color: #333;
            padding: 15px;
            margin-top: 20px;
            border-radius: 4px;
            border: none;
            color: white;
            resize: vertical;
        }

        @media screen and (max-width: 1024px) {
            .content {
                padding: 15px;
            }

            .product-layout {
                gap: 20px;
            }

            .category-list {
                width: 150px;
            }
        }

        @media screen and (max-width: 768px) {
            .product-layout {
                flex-direction: column;
            }

            .category-list {
                width: 100%;
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }

            .category-list,
            .image-section,
            .product-info {
                width: 100%;
            }

            .category-item {
                flex: 1;
                text-align: center;
                margin-bottom: 0;
            }

            .image-section {
                flex-direction: column-reverse;
            }

            .thumbnail-list {
                flex-direction: row;
                overflow-x: auto;
                width: 100%;
                padding-bottom: 10px;
            }

            .thumbnail {
                min-width: 80px;
                height: 80px;
                margin-right: 10px;
            }

            .product-info {
                width: 100%;
            }
        }

        @media screen and (max-width: 480px) {
            .content {
                padding: 10px;
            }

            .product-title {
                font-size: 20px;
            }

            .price {
                font-size: 18px;
            }

            .product-description {
                height: 150px;
            }

            .delivery-options {
                flex-direction: column;
                gap: 10px;
            }

            .thumbnail {
                min-width: 60px;
                height: 60px;
            }
        }

    </style>
</head>
<body>
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="main-container">
        <div class="content">
            <h1 class="product-title">상품 등록 페이지</h1>
            <div class="product-layout">
                <div class="category-list">
                    <div class="category-item">카테고리1</div>
                    <div class="category-item">카테고리1</div>
                    <div class="category-item">카테고리1</div>
                </div>

                <div class="image-section">
                    <div class="thumbnail-list">
                        <div class="thumbnail">상품 이미지</div>
                        <div class="thumbnail">상품 이미지</div>
                        <div class="thumbnail">상품 이미지</div>
                        <div class="thumbnail">상품 이미지</div>
                    </div>
                    <div class="main-image">썸네일 이미지 등록</div>
                </div>

                <div class="product-info">
                    <h2>상품 이름</h2>
                    <div class="price">₩10,000</div>
                    <div class="delivery-options">
                        <label><input type="radio" name="delivery"> 직거래</label>
                        <label><input type="radio" name="delivery"> 택배</label>
                    </div>
                    <textarea class="product-description" placeholder="상품 설명"></textarea>
                </div>
            </div>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const header_search = document.querySelector('.header-search');
        if (header_search) {
            header_search.style.display = 'none';
        }
    });

</script>