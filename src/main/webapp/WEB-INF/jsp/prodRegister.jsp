<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy</title>
    <script async src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/style/common.css"/>">
    <style>
        .content-left {
            flex: 0.2; /* 20% 너비 설정 */
            display: flex;
            flex-direction: column; /* 세로 방향으로 정렬 */
            justify-content: flex-start; /* 수평 가운데 정렬 */
            align-items: center; /* 수직 중앙 정렬 */
            background-color: #291b25; /* 배경색 */
            border-top: 1px solid #444; /* 구분선 */
            padding-top: 10px; /* 상단 여백 추가 */
        }
        label{
            min-width: 200px; /* 라벨의 최소 너비 설정 */
            text-align: center;
            padding: 5px;
            margin: 3px;
        }
        input{
            min-width: 200px;
        }
        .content-left select{
            min-width: 100px; /* 라벨의 최소 너비 설정 */
            text-align: center;
            padding: 5px;
            margin: 15px;
        }
        .content-center {
            flex: 0.5; /* 동일한 비율로 공간 차지 */
            display: flex;
            justify-content: center; /* 수평 가운데 정렬 */
            align-items: center; /* 수직 가운데 정렬 */
            background-color: #291b25; /* 배경색 추가 (테스트용) */
            border-top: 1px solid #444; /* 구분선 (테스트용) */
        }


        .content-right{
            flex: 0.3; /* 20% 너비 설정 */
            display: flex;
            flex-direction: column; /* 세로 방향으로 정렬 */
            justify-content: flex-start; /* 수평 가운데 정렬 */
            align-items: center; /* 수직 중앙 정렬 */
            background-color: #291b25; /* 배경색 */
            border-top: 1px solid #444; /* 구분선 */
            gap: 10px;
        }
        .order_status {
            padding: 15px 10px;
        }
        .order_status input[type=radio]{
            display: none;
        }
        .order_status input[type=radio]+label{
            display: inline-block;
            cursor: pointer;
            height: 24px;
            width: 90px;
            border: 1px solid #333;
            line-height: 24px;
            text-align: center;
            font-weight:bold;
            font-size:13px;
        }
        .order_status input[type=radio]+label{
            background-color: #fff;
            color: #333;
        }
        .order_status input[type=radio]:checked+label{
            background-color: #333;
            color: #fff;
        }
    </style>
</head>
<body>
<div id="wrap">
    <%@include file="include/header.jsp" %>
        <h1 style="color: white">상품 등록 페이지</h1>
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
            <h1 style="color: white">상품등록 페이지</h1>
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