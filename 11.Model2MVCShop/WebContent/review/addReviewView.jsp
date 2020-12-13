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

function fncAddReview() {
	$("form").attr("method", "POST").attr("action", "/review/addReview").submit();
}

$(function () {
	
	$("button:contains('내 구매내역 보기')").on("click", function() {
		fncAddReview();
	});

	$("button('취소')").on("click", function() {
		history.go(-1);
	});

});

</script>
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->	

<form name="addReview">

<div class="container">
	
		<div class="page-header">
	       <h1 class="text-center">REVIEW</h1>
	    </div>
	
		<input type="hidden" name="prodNo" value="${ product.prodNo }" />
		<input type="hidden" name="tranNo" value="${ purchase.tranNo }" />
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상 품 명</strong></div>
			<div class="col-xs-8 col-md-4">${ product.prodName }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>상 품 상 세 정 보</strong></div>
			<div class="col-xs-8 col-md-4">${ product.prodDetail }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>가 격</strong></div>
			<div class="col-xs-8 col-md-4">${ product.price }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>작 성 아 이 디</strong></div>
			<div class="col-xs-8 col-md-4">${ sessionScope.user.userId }</div>
			<input type="hidden" name="buyerId" value="${ sessionScope.user.userId }" />
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>주 문 날 짜</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.orderDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>평 점</strong></div>
			<div class="col-xs-8 col-md-4">
				<input type='radio' name='title' value='1' /> 1
  				<input type='radio' name='title' value='2' /> 2
  				<input type='radio' name='title' value='3' /> 3
 				<input type='radio' name='title' value='4' /> 4
 				<input type='radio' name='title' value='5' checked/> 5
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>내 용</strong></div>
			<div class="col-xs-8 col-md-4"> 
			<input		type="text" name="contents" 	class="form-control"
							style="width: 600px; height: 100px" maxLength="500" />
			</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
	  		
	  			<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target=".modal">등록</button>	 
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
        	<p>리뷰 작성이 완료되었습니다!</p>
     	 </div>
      	<div class="modal-footer">
       		 <button type="button" class="btn btn-primary">내 구매내역 보기</button>
      	</div>
    	</div>
  		</div>
		</div>
		
		<br/>
		
 	</div>

</form>
</body>
</html>