<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>구매상세조회</title>

	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 body >  div.container{ 
            margin-top: 50px;
        }
    </style>

	<!-- CDN(Content Delivery Network) 호스트 사용 -->
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	
		$(function() {
			
			$("button:contains('수정')").on("click", function () {
				self.location = "/purchase/updatePurchase?tranNo=${ purchase.tranNo }";
			});
			
			$("button:contains('확인')").on("click", function () {
				history.go(-1);
			});

		});
	
	</script>

</head>

<body bgcolor="#ffffff" text="#000000">

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->	

<div class="container">
	
		<div class="page-header">
	       <h1 class="text-center">PURCHASE INFO</h1>
	    </div>
	
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상 품 번 호</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.purchaseProd.prodNo }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 자 아 이 디</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.buyer.userId }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 방 법</strong></div>
			<div class="col-xs-8 col-md-4">${ fn:trim(purchase.paymentOption) eq '1' ? "현금구매" : "신용구매" }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>구 매 자 이 름</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.receiverName }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 자 연 락 처</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.receiverPhone }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 자 주 소</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.divyAddr }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 요 청 사 항</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.divyRequest }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>배 송 희 망 일</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.divyDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>주 문 일</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.orderDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
				<!-- 관리자이거나 배송중, 배송완료 상태일때 수정 불가 -->
				<c:if test = "${ ! (sessionScope.user.role == 'admin') }">
					<c:if test = "${ ! (purchase.tranCode eq '배송중' || purchase.tranCode eq '배송완료') }">
	  					<button type="button" class="btn btn-outline-primary">수정</button>
					</c:if>
				</c:if>
	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">확인</button>
	  		</div>
		</div>
		
		<br/>
		
 	</div>

</body>
</html>