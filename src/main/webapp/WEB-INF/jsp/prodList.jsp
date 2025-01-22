<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 25. 1. 21.
  Time: 오전 1:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy</title>
    <script src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/style/common.css"/>">
</head>
<body class="custom-scrollbar">
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="content">
        <%@include file="include/category.jsp" %>
        <div class="main-container">
            <div class="product-list">
                <c:forEach var="product" items="${productList}">
                    <div class="product">
                        <div class="product-image-container">
                            <script>
                                let imageUrls = JSON.parse('${product.imageUrls}');
                                if (imageUrls.length > 0) {
                                    document.write('<img src="' + imageUrls[0] + '" alt="Product Image" class="product-image"/>');
                                }
                            </script>
                        </div>
                        <div class="product-details">
                            <div class="product-title">
                                <c:out value="${product.prodName}"/>
                            </div>
                            <div class="product-price">
                                $<c:out value="${product.price}"/>
                            </div>
                            <div class="product-description">
                                <c:out value="${product.prodDesc}"/>
                            </div>
                            <a href="<c:url value="http://localhost/prod/${product.prodId}"/>"
                               class="product-spec-button">상세보기</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>