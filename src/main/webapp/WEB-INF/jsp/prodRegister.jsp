<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy</title>
    <script async src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/style/common.css"/>">
    <style>
        .main-container {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            grid-template-rows: 1fr 1fr 1fr;
            gap: 0px 0px;
            grid-template-areas:
                    "header header header"
                    "main main main"
                    "footer footer footer";
        }

        .content-left {
            display: flex;
            flex-direction: column;
            gap: 15px;
            padding: 15px;
        }

        .content-left label,
        .content-left select {
            width: 100%;
        }

        .content-center {
            display: grid;
            grid-template-columns: 100px auto;
            gap: 10px;
        }

        .thumbnail-list {
            display: flex;
            flex-direction: column; /* 세로 방향으로 정렬 */
            gap: 10px; /* 썸네일 간격 */
        }

        .thumbnail {
            width: 100px;
            height: 100px;
            background-color: #666; /* 썸네일 배경색 */
            display: flex;
            justify-content: center;
            align-items: center;
            color: white; /* 텍스트 색상 */
            border: 1px solid #444; /* 테두리 */
        }

        .main-thumbnail {
            width: 300px;
            height: 50%;
            background-color: #999; /* 메인 썸네일 배경색 */
            display: flex;
            justify-content: center;
            align-items: center;
            color: white; /* 텍스트 색상 */
            border: 1px solid #444; /* 테두리 */
        }

        .content-right {
            display: flex;
            flex-direction: column; /* 세로 방향으로 정렬 */
            gap: 10px;
        }

        .content-right input,
        .content-right textarea {
            width: 100%; /* 입력 필드 너비를 부모에 맞춤 */
        }

        .order_status {
            padding: 15px 10px;
        }

        .order_status input[type=radio] {
            display: none;
        }

        .order_status input[type=radio] + label {
            display: inline-block;
            cursor: pointer;
            height: 24px;
            width: 90px;
            border: 1px solid #333;
            line-height: 24px;
            text-align: center;
            font-weight: bold;
            font-size: 13px;
        }

        .order_status input[type=radio] + label {
            background-color: #fff;
            color: #333;
        }

        .order_status input[type=radio]:checked + label {
            background-color: #333;
            color: #fff;
        }

        @media (max-width: 1200px) {
            .main-container {
                grid-template-columns: 1fr; /* 모든 섹션을 세로로 배치 */
                gap: 20px; /* 섹션 간격 증가 */
                text-align: center
            }
        }

    </style>
</head>
<body>
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <h1 style="color: white">상품등록 페이지</h1>
    <div class="main-container" style="color: white">
        <div class="content-left">
            <label>카테고리</label>
            <select>
                <option>--------------</option>
            </select>
            <select>
                <option>dd1</option>
                <option>dd2</option>
            </select>
            <select>
                <option>ddd1</option>
                <option>ddd2</option>
            </select>
            <hr/>
        </div>
        <div class="content-center">
            <div class="thumbnail-list">
                <div class="thumbnail">상품 이미지 1</div>
                <div class="thumbnail">상품 이미지 2</div>
                <div class="thumbnail">상품 이미지 3</div>
                <div class="thumbnail">상품 이미지 4</div>
            </div>
            <div class="main-thumbnail">
                썸네일 이미지
            </div>
        </div>
        <div class="content-right">
            <label for="name">상품명</label>
            <input type="text" id="name" name="name" required>
            <label for="price">가격</label>
            <input type="text" id="price" name="price" required>
            <div class="order_status">
                <input type="radio" id="select" name="trade_type" value="0">
                <label for="select">직거래</label>
                <input type="radio" id="select2" name="trade_type" value="1">
                <label for="select2">택배거래</label>
            </div>
            <label for="description">상품 설명</label>
            <textarea id="description" name="description" required style="flex: 1; width: 400px"></textarea>
            <BUTTON type="submit">등록하기</BUTTON>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</div>

</body>
<script>
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

</script>
</html>