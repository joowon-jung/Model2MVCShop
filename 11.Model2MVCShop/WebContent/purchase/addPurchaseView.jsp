<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 div.container{ 
            margin-top: 50px;
        }
    </style>
    
<script type="text/javascript" src="../javascript/calendar.js"></script>
<!-- CDN(Content Delivery Network) 호스트 사용 -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

<script type="text/javascript">

function fncAddPurchase() {
	//document.addPurchase.submit();
	$("form").attr("method", "POST").attr("action", "/purchase/addPurchase").submit();
}

$(function () {
	
	$("img[src='../images/ct_icon_date.gif']").on("click", function() {
		show_calendar('document.addPurchase.divyDate', $("input[name='divyDate']").val());
	});
	
	$("button:contains('더 둘러보기')").on("click", function() {
		fncAddPurchase();
	});
	
	$("button:contains('취소')").on("click", function() {
		history.go(-1);
	});
	
});

</script>
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->	
   	
<form name="addPurchase">

	<div class="container">
	
		<div class="page-header">
	       <h1 class="text-center">PURCHASE</h1>
	    </div>
	
		<input type="hidden" name="prodNo" value="${ product.prodNo }" />
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상 품 번 호</strong></div>
			<div class="col-xs-8 col-md-4">${ product.prodNo }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>상 품 명</strong></div>
			<div class="col-xs-8 col-md-4">${ product.prodName }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>상 품 이 미 지</strong></div>
			<div class="col-xs-8 col-md-4"><img src="/images/uploadFiles/${ product.fileName }" width="200" height="200"/></div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>상 품 상 세 정 보</strong></div>
			<div class="col-xs-8 col-md-4">${ product.prodDetail }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>제 조 일 자</strong></div>
			<div class="col-xs-8 col-md-4">${ product.manuDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>가 격</strong></div>
			<div class="col-xs-8 col-md-4">${ product.price }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 자 아 이 디</strong></div>
			<div class="col-xs-8 col-md-4">${ sessionScope.user.userId }</div>
			<input type="hidden" name="buyerId" value="${ sessionScope.user.userId }" />
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 방 법</strong></div>
			<div class="col-xs-8 col-md-4">
				<select 	name="paymentOption" class="form-control"
							style="width: 200px; height: 50px" maxLength="20">
				<option value="1" selected="selected">현금구매</option>
				<option value="2">신용구매</option>
			</select>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 자 이 름</strong></div>
			<div class="col-xs-8 col-md-4">
			<input type="text" name="receiverName" 	class="form-control"
					style="width: 200px; height: 50px" maxLength="20" value="${ sessionScope.user.userName }" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>구 매 자 연 락 처</strong></div>
			<div class="col-xs-8 col-md-4">
			<input 	type="text" name="receiverPhone" class="form-control"
							style="width: 200px; height: 50px" maxLength="20" value="${ sessionScope.user.phone }" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>배 송 지 주 소</strong></div>
			<div class="col-xs-8 col-md-4">
			<input 	type="text" name="divyAddr" class="form-control"
							style="width: 400px; height: 50px" maxLength="20" 	value="${ sessionScope.user.addr }" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>배 송 시 요 청 사 항</strong></div>
			<div class="col-xs-8 col-md-4"> 
			<input		type="text" name="divyRequest" 	class="form-control"
							style="width: 400px; height: 50px" maxLength="30" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>배 송 희 망 일 자</strong>
	  		<img src="../images/ct_icon_date.gif" width="20" height="20" />
	  		</div>
			<div class="col-xs-8 col-md-4"> 
			<input 	type="text" readonly="readonly" name="divyDate" class="form-control"
							style="width: 200px; height: 50px" maxLength="20"/>
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
	  		
	  			<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target=".modal">구매</button>	 
	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">취소</button>
	  		</div>
		</div>
		
		<!-- MODAL 창 띄우기 -->
		<div class="modal">
  			<div class="modal-dialog" role="document">
    		<div class="modal-content">
      		<div class="modal-header">
       		 <h5 class="modal-title">SUCCESS</h5>
      		  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
         	<span aria-hidden="true">&times;</span>
        	</button>
     	 </div>
     	 <div class="modal-body">
        	<p>성공적으로 구매가 완료되었습니다!</p>
     	 </div>
      	<div class="modal-footer">
       		 <button type="button" class="btn btn-primary">더 둘러보기</button>
      	</div>
    	</div>
  		</div>
		</div>
		
		<br/>
		
 	</div>

</form>
</body>
</html>