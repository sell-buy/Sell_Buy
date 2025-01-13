<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 25. 1. 13.
  Time: 오후 4:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/style/common.css"/>">
<link rel="stylesheet" href="<c:url value="/style/memberReg.css"/>">
<script src="<c:url value="/script/modal.js"/>"></script>
<title></title>
<div class="modal">
    <div class="modal_body">
        <h1>Sell&Buy</h1>
        <form method="post" action="" id="register">
            <input type="text" name="id" placeholder="아이디">
            <input type="password" name="password" placeholder="비밀번호">
            <input type="password" name="password-check" placeholder="비밀번호 확인">
            <input type="text" name="name" placeholder="이름">
            <input type="text" name="nickname" placeholder="닉네임">
            <input type="text" name="phone" placeholder="전화번호">
            <input type="text" name="email" placeholder="이메일">
            <a href="#" id="href-open-login">이미 회원이신가요?</a>
            <button type="submit">회원가입</button>
        </form>
    </div>
</div>
</html>
