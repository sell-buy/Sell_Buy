<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy :: 내 정보</title>
    <script src="<c:url value="/webjars/jquery/3.7.1/dist/jquery.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/style/common.css"/>">
    <style>
        .main-container {
            display: flex;
            flex-direction: column;
            padding: 20px;
            color: white;
        }

        .member-info-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        .member-info-table th, .member-info-table td {
            border: 1px solid #444;
            padding: 8px;
            text-align: left;
        }

        .member-info-table th {
            background-color: #332831;
            color: white;
            width: 150px;
        }

        .section-title {
            font-size: 1.5em;
            font-weight: bold;
            margin-bottom: 15px;
        }

        .product-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .product-card {
            border: 1px solid #444;
            border-radius: 8px;
            padding: 15px;
            background-color: #332831;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .product-image-container {
            text-align: center;
            margin-bottom: 10px;
            width: 100%;
            height: 200px;
            overflow: hidden;
        }

        .product-card .product-image {
            max-width: 100%;
            height: 100%;
            border-radius: 8px;
            width: 100%;
            object-fit: cover;
            object-position: center;
        }

        .product-card .product-title {
            font-size: 1.1em;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .product-card .product-price {
            color: #e74c3c;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .product-card .product-description {
            color: #ddd;
            font-size: 0.9em;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            margin-bottom: 10px;
        }

        .product-list-empty {
            text-align: center;
            color: #ddd;
            padding: 20px;
        }

        .button-container {
            text-align: center;
            margin-top: 20px;
        }

        .update-button {
            padding: 10px 20px;
            background-color: #d79672;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
        }

        .more-link {
            display: block;
            text-align: center;
            margin-top: 10px;
            color: #d79672;
            text-decoration: none;
        }
    </style>
    <script>
        $(document).ready(function () {
            // 찜 목록 AJAX 요청 및 표시 (기존 코드 유지)
            fetch("http://localhost/fav/list?page=1&size=6", {
                method: "GET",
                credentials: "include"
            })
                .then(response => response.text())
                .then(data => {
                    $('#fav-list').html(data);
                    if ($('#fav-list .product-card').length === 0) {
                        $('#fav-list').html('<p class="product-list-empty">찜한 상품이 없습니다.</p>');
                    }
                })
                .catch(error => console.error("찜 목록 요청 실패:", error));

            // 판매 상품 목록 AJAX 요청 및 표시 (기존 코드 유지)
            fetch("http://localhost/prod/list/another?page=1&size=6&searchType=seller&searchQuery=${member.nickname}", {
                method: "GET",
                credentials: "include"
            })
                .then(response => response.text())
                .then(data => {
                    $('#sell-list').html(data);
                    if ($('#sell-list .product-card').length === 0) {
                        $('#sell-list').html('<p class="product-list-empty">판매 중인 상품이 없습니다.</p>');
                    }
                })
                .catch(error => console.error("판매 상품 목록 요청 실패:", error));
        });
    </script>
</head>
<body class="custom-scrollbar">
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="content">
        <%@include file="include/category.jsp" %>
        <div class="main-container">
            <h2>내 정보</h2>

            <table class="member-info-table">
                <tr>
                    <th>아이디</th>
                    <td><c:out value="${member.loginId}"/></td>
                </tr>
                <tr>
                    <th>닉네임</th>
                    <td><c:out value="${member.nickname}"/></td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td><c:out value="${member.name}"/></td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td><c:out value="${member.email}"/></td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td><c:out value="${member.phoneNum}"/></td>
                </tr>
                <tr>
                    <th>가입일시</th>
                    <td><c:out value="${member.createDate}"/></td>
                </tr>
            </table>

            <h3 class="section-title">찜한 상품</h3>
            <div id="fav-list" class="product-list">
                <!-- 찜 목록이 여기에 표시됩니다 (JavaScript) -->
            </div>
            <a href="<c:url value="/fav/list?page=1&size=99999"/>" class="more-link">찜 목록 더보기</a>

            <h3 class="section-title">판매 상품</h3>
            <div id="sell-list" class="product-list">
                <!-- 판매 상품 목록이 여기에 표시됩니다 (JavaScript) -->
            </div>
            <a href="<c:url value="/prod/list/another?page=1&size=99999&searchType=seller&searchQuery=${member.nickname}"/>"
               class="more-link">판매 상품 더보기</a>


            <div class="button-container">
                <button class="update-button" onclick="location.href='<c:url value="/member/update"/>'">회원 정보 수정
                </button>
            </div>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</div>
</body>
</html>
