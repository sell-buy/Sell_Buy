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
    <script>
        window.addEventListener('load', function () {
            console.log("memberInfo.jsp loaded");

            fetch("http://localhost/fav/list?page=1&size=6", {
                method: "GET",
                credentials: "include", // `xhrFields.withCredentials: true`에 해당
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.text(); // 데이터를 HTML 텍스트로 처리
                })
                .then(data => {
                    document.getElementById('fav-list').innerHTML = data; // 결과를 DOM에 삽입
                })
                .catch(error => {
                    console.error("Fetch request failed:", error);
                });
            fetch("http://localhost/prod/list/another?page=1&size=6&searchType=seller&searchQuery=${member.nickname}", {
                method: "GET",
                credentials: "include", // `xhrFields.withCredentials: true`에 해당
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.text(); // 데이터를 HTML 텍��트로 처리
                })
                .then(data => {
                    // 결과를 DOM에 삽입, modelAndView가 반환됨
                    document.getElementById('sell-list').innerHTML = data;
                })
                .catch(error => {
                    console.error("Fetch request failed:", error);
                });
        });

    </script>

    <style>
        .main-container {
            display: flex;
            flex-direction: column;
            margin-top: 50px;
        }


    </style>
</head>
<body class="custom-scrollbar">
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="content">
        <%@include file="include/category.jsp" %>
        <div class="main-container" style="color: white; width: 100%; ">
            ${member.printAll()}
            <hr>
            <div id="fav-list" style="color: white; width: 100%; ">
            </div>
            <hr>
            <div id="sell-list" style="color: white; width: 100%; "></div>
            <button onclick="window.location.href = 'http://localhost/member/update'">수정</button>
        </div>

    </div>
    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>
