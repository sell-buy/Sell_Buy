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
<link rel="stylesheet" href="<c:url value="/style/login.css"/>">
<script src="<c:url value="/script/modal.js"/>"></script>
<title></title>
<div class="modal">
    <div class="modal_body">
        <h1>Sell&Buy</h1>
        <form method="post" action="" id="login">
            <label>
                <input type="text" name="id" placeholder="아이디">
            </label>
            <label>
                <input type="password" name="password" placeholder="비밀번호">
            </label>
            <a href="#" id="href-open-register">아직 회원이 아니신가요?</a>
            <button type="submit">로그인</button>
        </form>
    </div>
</div>
</html>

