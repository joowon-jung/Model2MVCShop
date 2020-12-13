<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>

<title>구매정보 수정</title>

	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 div.container{ 
            margin-top: 50px;
        }
    </style>

<!-- CDN(Content Delivery Network) 호스트 사용 -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../javascript/calendar.js"></script>

<script type="text/javascript">

function fncUpdatePurchase() {
	
	$("form").attr("method", "POST").attr("action", "/purchase/updatePurchase?tranNo=${ purchase.tranNo }").submit();
}

$(function () {
	
	$("img[src='../images/ct_icon_date.gif']").on("click", function() {
		show_calendar('document.updatePurchase.divyDate', $("input[name='divyDate']").val());
	});
	
	$("button:contains('수정')").on("click", function() {
		fncUpdatePurchase();
	});
	
	$("button:contains('취소')").on("click", function() {
		history.go(-1);
	});
	
});

</script>

</head>

<body bgcolor="#ffffff" text="#000000">

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->	
   	
<form name="updatePurchase">

<div class="container">
	
		<div class="page-header">
	       <h1 class="text-center">UPDATE PURCHASE</h1>
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
			<div class="col-xs-8 col-md-4">
			<select 	name="paymentOption" class="form-control" style="width: 200px; height: 50px"
							maxLength="20">
				<option value="1" ${ fn:trim(purchase.paymentOption) eq '1' ? "selected" : "" }>현금구매</option>
				<option value="2" ${ fn:trim(purchase.paymentOption) eq '2' ? "selected" : "" }>신용구매</option>
			</select>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>구 매 자 이 름</strong></div>
			<div class="col-xs-8 col-md-4">
			<input 	type="text" name="receiverName" class="form-control" style="width: 200px; height: 50px" 
							maxLength="20" value="${ purchase.receiverName }" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 자 연 락 처</strong></div>
			<div class="col-xs-8 col-md-4">
			<input 	type="text" name="receiverPhone" class="form-control" style="width: 200px; height: 50px" 
							maxLength="20" value="${ purchase.receiverPhone }" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>배 송 지 주 소</strong></div>
			<div class="col-xs-8 col-md-4">
			<input 	type="text" name="divyAddr" class="form-control" style="width: 400px; height: 50px"
							maxLength="20" value="${ purchase.divyAddr }" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>배 송 시 요 청 사 항</strong></div>
			<div class="col-xs-8 col-md-4">
			<input 	type="text" name="divyRequest" 	class="form-control" style="width: 400px; height: 50px" 
							maxLength="30" value="${ purchase.divyRequest }" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>배 송 희 망 일 자</strong>
	  		<img src="../images/ct_icon_date.gif" width="20" height="20" />
	  		</div>
			<div class="col-xs-8 col-md-4">
			<input type="text" readonly="readonly" name="divyDate" class="form-control"
						style="width: 200px; height: 50px" maxLength="20" value = "${ purchase.divyDate }"/>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>주 문 일</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.orderDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
	  			<button type="button" class="btn btn-outline-primary">수정</button>
	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">취소</button>
	  		</div>
		</div>
		
		<br/>
		
 	</div>

</form>

</body>
</html>