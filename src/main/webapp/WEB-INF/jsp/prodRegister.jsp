<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 24. 12. 27.
  Time: 오후 1:09
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy</title>
    <script src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <link rel="stylesheet" href="<c:url value='/style/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/prodRegister.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/darkBackground.css'/>">
</head>
<body>
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="main-container">
        <h1>중고 상품 등록</h1>
        <form id="product-form">
            <label for="productName">상품 이름:</label>
            <input type="text" id="productName" name="productName" required> <label
                for="productDescription">상품 설명:</label>
            <textarea id="productDescription" name="productDescription" rows="4" required></textarea>
            <label for="productCategory">카테고리:</label>
            <select id="productCategory" name="productCategory">
                <option value="fashion">패션의류</option>
                <option value="electronics">가전제품</option>
                <option value="furniture">가구/인테리어</option>
                <!-- 추가 카테고리 --> </select>
            <label for="productPrice">가격:</label>
            <input type="number" id="productPrice" name="productPrice" required> <label for="productImage">상품
            이미지:</label> <input type="file" id="productImage" name="productImage" accept="image/*" required>
            <button type="submit">등록</button>
        </form>
    </div>

    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>
