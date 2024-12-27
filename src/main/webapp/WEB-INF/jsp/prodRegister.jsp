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
    <script async src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <link rel="stylesheet" href="<c:url value='/style/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/prodRegister.css'/>">
</head>
<body>
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="main-container">
        <%@include file="include/category.jsp" %>
        <div class="content">
            <form id="prodRegForm">
                <div class="form-group">
                    <label for="name">상품명</label>
                    <input type="text" id="name" name="name" required>
                </div>
                <div class="form-group">
                    <label for="price">가격</label>
                    <input type="number" id="price" name="price" required>
                </div>
                <div class="form-group">
                    <label for="description">상품 설명</label>
                    <textarea id="description" name="description" required></textarea>
                </div>
                <%--<div class="form-group">
                    <label for="category">카테고리</label>
                    <input type="text" id="category" name="category" required>
                </div>--%>
                <%--<div class="form-group">
                    <label for="image">상품 이미지</label>
                    <input type="file" id="image" name="image" required>
                </div>--%>
                <button type="submit">등록</button>
            </form>
        </div>
    </div>

    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>
