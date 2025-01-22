<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 25. 1. 22.
  Time: 오후 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy</title>
    <script src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/style/common.css"/>">

    <style>
        .main-container {
            display: flex;
            flex-direction: column;
            margin-top: 50px;
        }


    </style>

    <script>
        $
    </script>
</head>
<body class="custom-scrollbar">
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="content">
        <%@include file="include/category.jsp" %>
        <div class="main-container" style="color: white; width: 100%; ">
            <h1>회원 정보 수정</h1>
            <form action="http://localhost/member" id="">
                <input type="hidden" name="id" value="${member.id}">
                <div>
                    <label for="nickname">닉네임</label>
                    <input type="text" id="nickname" name="nickname" value="${member.nickname}" required>
                </div>
                <div>
                    <label for="oldPassword">기존 비밀번호</label>
                    <input type="password" id="oldPassword" name="oldPassword" required>
                </div>
                <div>
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div>
                    <label for="passwordCheck">비밀번호 확인</label>
                    <input type="password" id="passwordCheck" name="passwordCheck" required>
                </div>
                <div>
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" value="${member.name}" required>
                </div>
                <div>
                    <label for="email">이메일</label>
                    <input type="email" id="email" name="email" value="${member.email}" required>
                </div>
                <div>
                    <label for="phone">전화번호</label>
                    <input type="tel" id="phone" name="phone" value="${member.phone}" required>
                </div>
                <div>
                    <button type="submit">수정</button>
                </div>
            </form>
        </div>

    </div>
    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>
