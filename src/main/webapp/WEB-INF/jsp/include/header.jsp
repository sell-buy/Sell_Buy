<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 24. 12. 24.
  Time: 오후 1:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script async src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <script async src="<c:url value="/webjars/bootstrap/5.3.3/js/bootstrap.js"/>"></script>
    <script async src="<c:url value="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.js"/>"></script>
    <script async src="<c:url value="/webjars/bootstrap/5.3.3/js/bootstrap.esm.js"/>"></script>
    <link rel="stylesheet" href="<c:url value='/style/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/header.css'/>">
    <title></title>
</head>
<body>
<header class="header">
    <div class="header-top">
        <nav class="header-menu">
            <ul>
                <li><a href="/notifications">알림</a></li>
                <li><a href="/customer-center">고객센터</a></li>
                <li><a href="/my-page">내 정보</a></li>
                <li><a href="/login">로그인</a></li>
            </ul>
        </nav>
    </div>
    <div class="header-logo">
        <a href="/">Sell&Buy</a>
    </div>
    <div class="header-search">
        <input type="text" placeholder="상품 검색"/>
        <button type="button">🔍</button>
    </div>
</header>
</body>
</html>
