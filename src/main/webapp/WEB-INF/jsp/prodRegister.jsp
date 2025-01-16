<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sell&Buy</title>
    <script async src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <script async src="<c:url value="/script/prodRegister.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/style/common.css"/>">
    <link rel="stylesheet" href="<c:url value="/style/prodRegister.css"/>">
</head>
<body>
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="content">
        <div class="main-container">
            <div class="content-left">
                <form id="prodRegisterForm" class="register-form">
                    <label for="prodName">
                        <input type="text" id="prodName" name="prodName" placeholder="상품 이름" required>
                    </label>
                    <label for="price">
                        <input type="text" id="price" name="price" placeholder="상품 가격" required>
                    </label>
                    <div class="prodType">
                        <input type="checkbox" id="type_direct" name="trade_type" value="0">
                        <label for="type_direct">직거래</label>
                        <input type="checkbox" id="type_delivery" name="trade_type" value="1">
                        <label for="type_delivery">택배거래</label>
                    </div>
                    <input type="hidden" id="category">
                    <label for="prodDesc">
                        <textarea id="prodDesc" name="prodDesc" placeholder="상품 설명" required style=""></textarea>
                    </label>
                    <button type="submit">등록하기</button>
                </form>
            </div>
            <div class="content-center">
                <div class="category-dep1">
                    <ul id="category-dep1"></ul>
                </div>
                <div class="category-dep2">
                    <ul id="category-dep2"></ul>
                </div>
                <div class="category-dep3">
                    <ul id="category-dep3"></ul>
                </div>
            </div>
            <div class="content-right">
                <h1>상품등록 페이지</h1>
            </div>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>
