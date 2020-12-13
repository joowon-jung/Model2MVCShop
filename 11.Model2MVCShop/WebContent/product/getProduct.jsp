<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

<title>상품상세조회</title>

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
			
			$("button:contains('확인')").on("click", function () {
				self.location = "/product/listProduct?menu=manage";
			});
			
			$("button:contains('BUY')").on("click", function () {
				self.location = "/purchase/addPurchase?prod_no=${ vo.prodNo }"
			});
			
			$("button:contains('이전')").on("click", function () {
				history.go(-1); 
			});

		});
	
	</script>
	
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->	


	<div class="container">
	
		<div class="page-header">
	       <h1 class="text-center">PRODUCT INFO</h1>
	    </div>
	
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>상 품 번 호</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.prodNo }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>상 품 명</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.prodName }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>상 품 이 미 지</strong></div>
			<div class="col-xs-8 col-md-4"><img src="/images/uploadFiles/${ vo.fileName }" width="200" height="200"/></div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>상 품 상 세 정 보</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.prodDetail }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>제 조 일 자</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.manuDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>가 격</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.price }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>등 록 일 자</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.regDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
	  		<!-- 상품 수정하고 넘어왔을 때 이 버튼 보이게 함 -->
	  		<c:if test = "${ menu eq 'ok' }" > 
	  			<button type="button" class="btn btn-outline-primary">확인</button>
	  		</c:if>
	  		<!-- 비회원 & 관리자는 상품 구매 불가하게 함 -->
			<c:if test = "${ ! empty sessionScope.user && ! (sessionScope.user.role eq 'admin') }">
			<c:if test = "${ vo.proPurchase.tranNo eq '0' }" > <!-- 이미 구매된 상품이면 구매 불가 -->
	  			<button type="button" class="btn btn-outline-primary">BUY</button>
	  		</c:if>
	  		</c:if>
	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">이전</button>
	  		</div>
		</div>
		
		<br/>
		
 	</div>

</body>
</html>