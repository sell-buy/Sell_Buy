<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 25. 1. 20.
  Time: 오전 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy - Product Details</title>
    <script src="<c:url value='/webjars/jquery/3.7.1/dist/jquery.js'/>"></script>
    <link rel="stylesheet" href="<c:url value='/style/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/prodSpec.css'/>">
</head>
<body class="custom-scrollbar">
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="content">
        <%@include file="include/category.jsp" %>
        <div class="main-container">
            <div class="product-image-container">
                <c:forEach var="imageUrl" items="${imageUrls}">
                    <img src="${imageUrl}" alt="Product Image" class="product-image"/>
                </c:forEach>
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
                <button class="purchase-button">구매</button>
            </div>
            <sec:authorize access="isAuthenticated()">
                <c:if test="${product.sellerId == memId}"> <%--memId 하면 왠지 몰라도 세션에 memId가 불러와짐 ㅋㅋ--%>
                    <div class="product-control">
                        <button class="product-control-button" id="btn-modify">수정</button>
                        <button class="product-control-button" id="btn-delete" onclick="window.location.href ">삭제
                        </button>
                    </div>
                </c:if>
            </sec:authorize>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>