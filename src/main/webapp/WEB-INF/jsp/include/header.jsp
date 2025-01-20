<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 24. 12. 24.
  Time: 오후 1:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <script src="<c:url value="/webjars/bootstrap/5.3.3/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/webjars/bootstrap/5.3.3/js/bootstrap.bundle.js"/>"></script>
    <script src="<c:url value="/webjars/bootstrap/5.3.3/js/bootstrap.esm.js"/>"></script>
    <link rel="stylesheet" href="<c:url value='/style/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/header.css'/>">
    <script src="<c:url value='/script/modal.js'/>"></script>
</head>
<div id="modalContainer"></div>
<body>
<header class="header">
    <div class="header-top">
        <nav class="header-menu">
            <ul>
                <li><a href="/notifications">알림</a></li>
                <li><a href="/customer-center">고객센터</a></li>
                <li><a href="/my-page">내 정보</a></li>
                <sec:authorize access="isAuthenticated()">
                    <li><a href="/member/logout">로그아웃</a></li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li><a href="#" id="href-open-login-header">로그인</a></li>
                </sec:authorize>
            </ul>
        </nav>
    </div>
    <div class="header-logo">
        <a href="/">Sell&Buy</a>
    </div>
</header>
<div class="search-bar">
    <label>
        <input type="text" placeholder="상품 검색"/>
    </label>
    <button type="button">🔍</button>
</div>
</body>
</html>
