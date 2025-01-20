<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
                <form class="register-form">
                    <label for="prodName">
                        <input type="text" id="prodName" name="prodName" placeholder="상품 이름" required>
                    </label>
                    <label for="price">
                        <input type="text" id="price" name="price" placeholder="상품 가격" required>
                    </label>
                    <div class="prodType">
                        <input type="radio" id="select" name="trade_type" value="0">
                        <label for="select">직거래</label>
                        <input type="radio" id="select2" name="trade_type" value="1">
                        <label for="select2">택배거래</label>
                    </div>
                    <label for="prodDesc">
                        <textarea id="prodDesc" name="prodDesc" placeholder="상품 설명" required style=""></textarea>
                    </label>
                    <BUTTON type="submit">등록하기</BUTTON>
                </form>
            </div>
            <div class="content-center">
                <div id="category-dep1">
                    <ul></ul>
                </div>
                <div id="category-dep2">
                    <ul></ul>
                </div>
                <div id="category-dep3">
                    <ul></ul>
                </div>
            </div>
            <div class="content-right">
                <h1>상품등록 페이지</h1>
            </div>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</body>
</html>