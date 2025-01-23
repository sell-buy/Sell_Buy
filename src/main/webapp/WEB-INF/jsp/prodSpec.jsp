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
<script src="https://js.tosspayments.com/v2/standard"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sell&Buy :: Product Details</title>
    <script src="<c:url value='/webjars/jquery/3.7.1/dist/jquery.js'/>"></script>
    <link rel="stylesheet" href="<c:url value='/style/common.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/prodSpec.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/slider.css'/>">
    <%-- 이미지 슬라이더 스타일 --%>
    <script src="<c:url value='/script/slider.js'/>"></script>
    <%-- 이미지 슬라이더 스크립트 --%>
    <script>
        let product = ${product}; // JSTL 로부터 상품 정보를 JavaScript 객체로 전달

        function deleteProd() {
            if (confirm("상품을 삭제하시겠습니까?")) {
                $.ajax({
                    url: `http://localhost/prod/${product.prodId}`,
                    type: 'DELETE',
                    success: function () {
                        alert('상품이 삭제되었습니다.');
                        window.location.href = 'http://localhost/prod/list';
                    },
                    error: function (xhr, status, error) {
                        console.error('상품 삭제 실패', error);
                        alert('상품 삭제에 실패했습니다.');
                    }
                });
            }
        }

        function toggleFavorite(prodId) {
            $.ajax({
                url: `http://localhost/fav?prodId=` + prodId,
                type: 'POST',
                success: function () {
                    window.location.reload(); // 찜 상태 변경 후 페이지 새로고침
                },
                error: function (xhr, status, error) {
                    console.error('찜하기 실패', error);
                    alert('찜하기에 실패했습니다.');
                }
            });
        }

        $(function () {
            $.get(`http://localhost/fav?prodId=${product.prodId}`, function (data) {
                if (data) {
                    $('#toggle-favorite').text('❤️ 찜 취소'); // 찜 O: 하트 아이콘 + "찜 취소"
                } else {
                    $('#toggle-favorite').text('🤍 찜하기'); // 찜 X: 빈 하트 아이콘 + "찜하기"
                }
            });
        });
    </script>
</head>
<body class="custom-scrollbar">
<div id="wrap">
    <%@include file="include/header.jsp" %>
    <div class="content">
        <%@include file="include/category.jsp" %>
        <div class="main-container prod-spec-container"> <%-- prod-spec-container 클래스 추가 --%>
            <div class="product-image-container">
                <div class="slider-container"> <%-- 이미지 슬라이더 컨테이너 --%>
                    <div class="slider-wrapper">
                        <ul class="slides">
                            <c:forEach var="imageUrl" items="${imageUrls}" varStatus="status">
                                <li class="slide">
                                    <img src="${imageUrl}" alt="Product Image ${status.index + 1}"
                                         class="product-image"/>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="slider-controls"> <%-- 슬라이더 컨트롤 --%>
                        <button class="prev-slide"><</button>
                        <button class="next-slide">></button>
                    </div>
                    <ul class="slide-indicators"> <%-- 슬라이드 인디케이터 --%>
                        <c:forEach var="imageUrl" items="${imageUrls}" varStatus="status">
                            <li class="slide-indicator" data-slide="${status.index}"></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="product-details">
                <h2 class="product-title">
                    <c:out value="${product.prodName}"/>
                </h2>
                <div class="product-price">
                    $ <c:out value="${product.price}"/>
                </div>
                <p class="product-description">
                    <c:out value="${product.prodDesc}"/>
                </p>

                <div class="seller-info"> <%-- 판매자 정보 영역 --%>
                    <p>판매자: <strong>${product.sellerId}</strong></p> <%-- TODO: 판매자 닉네임으로 변경 --%>
                    <%-- TODO: 판매자 평점 또는 다른 정보 추가 --%>
                </div>

                <div class="button-container"> <%-- 버튼 컨테이너 --%>
                    <button class="purchase-button">구매하기</button>
                    <sec:authorize access="isAuthenticated()">
                        <c:if test="${product.sellerId == memId}"> <%-- 판매자인 경우 --%>
                            <button class="product-control-button modify-button"
                                    onclick="window.location.href = `http://localhost/prod/update/${product.prodId}`">수정
                            </button>
                            <button class="product-control-button delete-button" onclick="deleteProd()">삭제</button>
                        </c:if>
                        <c:if test="${product.sellerId != memId}"> <%-- 구매자인 경우 --%>
                            <button id="toggle-favorite" class="favorite-button"
                                    onclick="toggleFavorite(${product.prodId})">🤍 찜하기
                            </button>
                        </c:if>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()"> <%-- 비회원인 경우 --%>
                        <button class="favorite-button" onclick="alert('로그인 후 찜하기 기능을 이용할 수 있습니다.')">🤍 찜하기</button>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div>
    <%@include file="include/footer.jsp" %>
</div>
<script>
    const button = document.getElementById('purchase-button')
    button.addEventListener('click', () => {
        window.open(`/payment/${product.prodId}?productId=${product.prodName}&price=${product.price}`,
            'PaymentPopup',
            'width=800,height=600,scrollbars=yes,resizable=no');

    })
</script>

</body>
</html>