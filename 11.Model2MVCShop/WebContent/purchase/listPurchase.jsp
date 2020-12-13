<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>구매 내역 조회</title>

<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.css" >
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 div.container{ 
            margin-top: 50px;
        }
        
        .wrap{width:30%; text-align:center; margin:0px auto;}
    </style>

<script type="text/javascript">

function fncGetList(currentPage){ // 안 씀 - 검색기능이 없어서 빼도 될 듯? 나중에 검색기능 추가하려면 쓰기
	
	$("#currentPage").val(currentPage);
	$("form").attr("method", "POST").attr("action", "/purchase/listPurchase").submit();
}

$(function() {
		
	$(".getPurchase").on("click", function () {
		self.location = "/purchase/getPurchase?tranNo="+$(this).attr('id');
	});
	
	$(".getProduct").on("click", function () { // 제품명 누르면 정보 보여지게 함
		self.location = "/product/getProduct?prodNo="+$(this).attr('id')+"&menu=search";
	});
	
	$(".badge.badge-success").on("click", function () { // 리뷰 등록
		self.location = "/review/addReview?prodNo="+$(this).attr('id')+"&tranNo="+$(this).attr('value');
	});
	
	$(".badge.badge-info").on("click", function () { // 리뷰 조회
		self.location = "/review/getReview?tranNo="+$(this).attr('id')+"&menu=own";
	});
	
	$(".badge.badge-warning").on("click", function () { // 상품 도착 -> 배송완료
		self.location = "/purchase/updateTranCode?tranNo="+$(this).attr('id')+"&tranCode=3";
	});
	
	//==> LINK Event End User 에게 보일수 있도록 
	$( "td:nth-child(2)" ).css("color" , "black");
	
	//==> LINK Event End User 에게 보일수 있도록 
	$( "td:nth-child(3)" ).css("color" , "black");
	
});

</script>
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->
<form name="detailForm">

<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">
	
		<div class="page-header text-center">
	       <h1>PURCHASE LIST</h1>
	    </div>
	    
	    <!-- table 위쪽 /////////////////////////////////////-->
	    <div class="row">
	    
		    <div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		<br>전체  ${ resultPage.totalCount } 건수,	 현재 ${ resultPage.currentPage } 페이지
		    	</p>
		    </div>
	    	
	    	<!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
	    	
		</div>
		<!-- table 위쪽 /////////////////////////////////////-->
		
		
      <!--  table Start /////////////////////////////////////-->
      <table class="table table-hover">
      
        <thead>
   			 <tr>
      			<th scope="col">주문날짜</th>
      			<th scope="col">주문번호</th>
      			<th scope="col">제품</th>
      			<th scope="col">가격</th>
      			<th scope="col">현재상태</th>
      			<th scope="col">REVIEW</th>
      			<th scope="col">상태수정</th>
    		</tr>
  		</thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		  <c:forEach var = "purchase" items = "${ list }">
			<c:set var = "i" value = "${ i + 1 }"/>
			<tr class="table-light">
			  <td align="left">${ purchase.orderDate }</td>
			  <td align="left">
			  	<span class = "getPurchase" id = "${ purchase.tranNo }">
					<b>${ purchase.tranNo }</b>
				</span>
			  </td>
			  <td align="left">
			  	<span class = "getProduct" id = "${ purchase.purchaseProd.prodNo }">
			  		<b>${ purchase.purchaseProd.prodName }</b>
			  		<br>
			  		<img src="/images/uploadFiles/${ purchase.purchaseProd.fileName }" width="100" height="100"/>
			   </span>
			  </td>
			  <td align="left">${ purchase.purchaseProd.price }</td>
			  <td align="left">${ purchase.tranCode }</td>
			  <td align="left">
			  	<c:if test = "${ purchase.tranCode eq '배송완료' && purchase.reviewNo==0 }">
			  		<span class = "badge badge-success" id = "${ purchase.purchaseProd.prodNo }"  value = "${ purchase.tranNo }">
			  		상품평 등록
					</span>
				</c:if>
				<c:if test = "${ purchase.tranCode eq '배송완료' && purchase.reviewNo!=0 }">
					<span class = "badge badge-info" id = "${ purchase.tranNo }">
					상품평 보기
					</span>
				</c:if>
			  </td>
			  <td align="left">
			  	<c:if test = "${ purchase.tranCode eq '배송중' }">
			  		<span class = "badge badge-warning" id = "${ purchase.tranNo }">
			  		상품 도착
			  		</span>
			  	</c:if>
			  </td>
			</tr>
          </c:forEach>
        
        </tbody>
      
      </table>
	  <!--  table End /////////////////////////////////////-->
	  
	  	<div class="wrap">
	   	<!-- PageNavigation Start... -->
		<jsp:include page="../common/pageNavigator_new.jsp"/>
		<!-- PageNavigation End... -->
		</div>

 	</div>
 	<!--  화면구성 div End /////////////////////////////////////-->
	</form>
</body>
</html>