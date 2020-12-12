<!-- addProductView 한 정보를 읽어서 확인 누르면 상품관리, 추가등록 누르면 addProductView로 이동 -->
<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>상품등록</title>

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
			
			$("button:contains('추가등록')").on("click", function () {
				self.location = "../product/addProductView.jsp";
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
	       <h3 class="text-center">ADD PRODUCT</h3>
	    </div>
		
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
	  		<div class="col-md-12 text-center ">

	  			<button type="button" class="btn btn-outline-primary">추가등록</button>

	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">확인</button>
	  		</div>
		</div>
		
		<br/>
		
 	</div>

</body>
</html>
