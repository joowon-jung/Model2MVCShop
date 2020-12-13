<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.css" >
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>

<!--  ///////////////////////// CSS ////////////////////////// -->
<style>
div.container {
	margin-top: 50px;
}

.wrap {
	width: 40%;
	text-align: center;
	margin: 0px auto;
}
</style>

<script type="text/javascript">

//검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
function fncGetList(currentPage){
	
	$("#currentPage").val(currentPage);
   	$("form[name='detailForm']").attr("method", "POST").attr("action", "/product/listProduct").submit();
   	
}

$(function() {
	
	$("input:checkbox[name='findby']").on("click", function () {
		fncGetList(1);
	});
	
	$("button:contains('검색')").on("click", function () {
		fncGetList(1);
	});
	
	$(".getProduct").on("click", function () {
		self.location = "/product/getProduct?prodNo="+$(this).attr('id')+"&menu=${ menu }";
	});
	
	//==> prodName LINK Event End User 에게 보일수 있도록 
	$(".getProduct").css("color" , "black");
	
	$(".getPurchase").on("click", function () {
		self.location = "/purchase/getPurchase?tranNo="+$(this).attr('id');
	});
	
	$(".badge.badge-success").on("click", function () { // 배송
		self.location = "/purchase/updateTranCodeByProd?prodNo="+$(this).attr('id')+"&tranCode=2";
	});
	
	$(".badge.badge-danger").on("click", function () { // 상품 삭제
		self.location = "/product/deleteProduct?prodNo="+$(this).attr('id');
	});
	
 });
	
	
$(function () {
		
	var autocomplete_text = [];
	
	$( "#tags" ).one("click" , function() { // one : 클릭시 한번만 작동하도록 
		$.ajax(
				{
					url : "/product/json/listProduct" ,
					method : "POST" ,
					dataType : "json" ,
					headers : {
						"Accept" : "application/json",
						"Content-Type" : "application/json"
					},
					data : JSON.stringify({ 
						searchKeyword : $('input[name="searchKeyword"]').val() ,
						searchCondition : $('input[name="searchCondition"]').val()
					}),
					success : function(JSONData, status) {
						
						//Debug...
						//alert(status);
					 
						for(var i=0; i < JSONData.length; i++){
							//alert("이름 : " + JSONData[i].prodName);
							autocomplete_text.push(JSONData[i].prodName);
						}
					}
					
		});
	});
	
	$( "#tags" ).autocomplete({
	      source: autocomplete_text
	});
	
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
	       <h1>${ menu eq 'manage' ? "PRODUCT MANAGE" : "PRODUCT LIST" }</h1>
	    </div>
	    
	    <br><br>
	    <!-- table 위쪽 검색 Start /////////////////////////////////////-->
	    <div class="row">
	    
		    <div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체 ${ resultPage.totalCount } 건수, 현재 ${ resultPage.currentPage} 페이지
		    	</p>
		    	<c:if test = "${ menu eq 'manage' }">
					<input type="checkbox" name = "findby" value = "sale" ${ search.findby eq 'sale' ? "checked" : ""}/><b> FOR SALE</b>
					<input type="checkbox" name = "findby" value = "sold" ${ search.findby eq 'sold' ? "checked" : ""}/><b> SOLD</b>
				</c:if>
		    </div>
		   
		    <div class="col-md-6 text-right">
		    	<div class="row">
			      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <div class="col-md-4">
				    <select class="form-control" name="searchCondition" >
						<option value="0" ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
						<option value="1" ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
						<option value="2" ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
					</select>
				  </div>
				  
				  <div class="col-md-5">
				    <label class="sr-only" for="searchKeyword">검색어</label>
				    <input type="text" id="tags" class="form-control" name="searchKeyword"  placeholder="검색어"
				    			 value="${! empty search.searchKeyword ? search.searchKeyword : '' }"  >
				  </div>
				  
				   <button type="button" class="btn btn-outline-primary">검색</button>
				  
				  <!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
				  <input type="hidden" id="currentPage" name="currentPage" value=""/>
	    		</div>
	    	</div>
	    	
		</div>
		<!-- table 위쪽 검색 Start /////////////////////////////////////-->
		
		
      <!--  table Start /////////////////////////////////////-->
      <table class="table table-hover">
      
        <thead>
   			 <tr>
      			<th align="center" scope="col">No</th>
      			<th scope="col">상품명</th>
      			<th scope="col">가격</th>
      			<th scope="col">
      			<c:if test = "${ menu eq 'manage' }">등록일자</c:if>
				<c:if test = "${ menu eq 'search' }">상세정보</c:if>
				</th>
      			<th scope="col">현재상태</th>
    		</tr>
  		</thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		  <c:forEach var = "product" items = "${ list }">
			<c:set var="i" value="${ i+1 }" />
			<tr class="table-light">
			  <td align="left">${ i }</td>
			  <td align="left">
			  		<c:if test = "${ product.proTranCode eq '판매중' || menu eq 'search' && product.proTranCode ne '판매중'}" >
						<span class = "getProduct" id = "${product.prodNo}">
							${ product.prodName }
						</span>
					</c:if>
					<!-- 관리자 일 때 재고없는 상품명 누르면 구매조회 뜨게 하기 -->
					<c:if test = "${ menu eq 'manage' && product.proTranCode ne '판매중' }" >
						<span class = "getPurchase" id = "${ product.proPurchase.tranNo }">
							${ product.prodName }					
						</span>
					</c:if>
			  </td>
			  <td align="left">${ product.price }</td>
			  <td align="left">
			  	<c:if test = "${ menu eq 'manage' }">${ product.regDate }</c:if>
				<c:if test = "${ menu eq 'search' }">${ product.prodDetail }</c:if>
			  </td>
			  <td align="left">
				<c:if test = "${ menu eq 'manage' }">
					${ product.proTranCode }
					<c:if test = "${ product.proTranCode eq '구매완료' }">
						<span class = "badge badge-success" id = "${product.prodNo}">
						SHIP
						</span>
					</c:if>
					<!-- 추후 상품 삭제 플래그처리 하기 ★ -->
					<c:if test = "${ product.proTranCode eq '판매중' }">
						<span class = "badge badge-danger" id = "${product.prodNo}">
						DELETE
						</span>
					</c:if>
				</c:if>
					<c:if test = "${ menu eq 'search' }">
						<c:if test = "${ product.proTranCode eq '판매중' }">
						판매중
						</c:if>
						<c:if test = "${ ! (product.proTranCode eq '판매중') }">
						재고없음
						</c:if>
					</c:if>
			 	</td>
			</tr>
          </c:forEach>
        
        </tbody>
      
      </table>
	  <!--  table End /////////////////////////////////////-->
	  
	  	<input type="hidden" id="menu" name="menu" value="${ menu }"/>
	  
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