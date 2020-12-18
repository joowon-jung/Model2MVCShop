<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>${ menu eq 'manage' ? "상품 관리" : "상품 목록조회" }</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">

<!-- CDN(Content Delivery Network) 호스트 사용 -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

//검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
function fncGetList(currentPage){
	
	$("#currentPage").val(currentPage);
   	$("form").attr("method", "POST").attr("action", "/product/listProduct").submit();
   	
}

$(function() {
	
	$("input:checkbox[name='findby']").on("click", function () {
		fncGetList(1);
	});
	
	$("td.ct_btn01:contains('검색')").on("click", function () {
		fncGetList(1);
	});
	
	$(".ct_list_pop td:nth-child(3) .getProduct").on("click", function () {
		self.location = "/product/getProduct?prodNo="+$(this).attr('id')+"&menu=${ menu }";
	});
	
	$(".ct_list_pop td:nth-child(3) .getPurchase").on("click", function () {
		self.location = "/purchase/getPurchase?tranNo="+$(this).attr('id');
	});
	
	$(".ct_list_pop td:nth-child(9) .updateTranCodeByProd").on("click", function () {
		self.location = "/purchase/updateTranCodeByProd?prodNo="+$(this).attr('id')+"&tranCode=2";
	});
	
	$(".ct_list_pop td:nth-child(9) .deleteProduct").on("click", function () {
		self.location = "/product/deleteProduct?prodNo="+$(this).attr('id');
	});
	
	//$( ".ct_list_pop td:nth-child(5)" ).on("click" , function() {
// 	$( "#tags" ).on("click" , function() {
// 		$.ajax(
// 				{
// 					url : "/product/json/listProduct" ,
// 					method : "POST" ,
// 					dataType : "json" ,
// 					headers : {
// 						"Accept" : "application/json",
// 						"Content-Type" : "application/json"
// 					},
// 					data : JSON.stringify({ 
// 						searchKeyword : $('input[name="searchKeyword"]').val() ,
// 						searchCondition : $('input[name="searchCondition"]').val()
// 					}),
// 					success : function(JSONData, status) {
						
// 						//Debug...
// 						//alert(status);
					 
// 						for(var i=0;i<JSONData.length;i++){
// 							//alert("이름 : " + JSONData[i].prodName);
// 							autocomplete_text.push(JSONData[i].prodName);
// 						}
// 					}
					
// 				});
// 	});

// 	$( "#tags" ).autocomplete({
// 	      source: autocomplete_text
// 	 });
	
	
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

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
							
						${ menu eq 'manage' ? "상품 관리" : "상품 목록조회" }
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<!-- 추후 수정 | 고객 & 관리자 구분하여 정렬 -->
<%-- 		<c:if test = "${ menu eq 'search' }">  --%>

<!-- 		<td> -->
<!-- 			<a href="javascript:fncGetList('1');"> -->
<!-- 			<input type="hidden" id="sorting" name="sorting" value="byPrice"/> -->
<!-- 			<b>가격높은순</b> -->
<!-- 			</a> -->
<!-- 		</td> -->
<!-- 		<td> -->
<!-- 			<a href="javascript:fncGetList('1');"> -->
			
<!-- 			<b>최신순</b> -->
<!-- 			</a> -->
<!-- 		</td> -->
<%-- 		</c:if> --%>
		<td>
		<c:if test = "${ menu eq 'manage' }">
			<input type="checkbox" name = "findby" value = "sale" ${ search.findby eq 'sale' ? "checked" : ""}/><b>판매중인 상품</b>
			<input type="checkbox" name = "findby" value = "sold" ${ search.findby eq 'sold' ? "checked" : ""}/><b>판매된 상품</b>
		</c:if>
		</td>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1" ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
			</select>
			<input type="text" id="tags" name="searchKeyword" value = "${! empty search.searchKeyword ? search.searchKeyword : "" }"
						class="ct_input_g" style="width:200px; height:19px" />
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${ resultPage.totalCount } 건수, 현재 ${ resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">
			<c:if test = "${ menu eq 'manage' }">등록일자</c:if>
			<c:if test = "${ menu eq 'search' }">상세정보</c:if>
		</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var = "i" value = "0" />
	<c:forEach var = "product" items = "${ list }">
		<c:set var = "i" value = "${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td> <!-- No : 1 2 3 표시되는 부분 --> 
			<td></td>
			<td align="left">
					<c:if test = "${ product.proTranCode eq '판매중' || menu eq 'search' && product.proTranCode ne '판매중'}" >
						<span class = "getProduct" id = "${product.prodNo}">
							<!-- 하나씩 뽑았으니까 접근 할 수 있음! 여기서는 list에 지정해줬으니까 아는것임!! 하지만 화면단은 list에 저장되어 있는 지 모르기때문에
							list[0].어쩌구.어쩌구로 접근해야하는데 그렇게되면 한 개의 상품번호만 들어가게되니까 그냥 아예 어디에 담는게 좋을거같음!!!!! -->
<%-- 							<a href="/product/getProduct?prodNo=${ product.prodNo }&menu=${ menu }"> --%>
							${ product.prodName }
						</span>
					</c:if>
					<!-- 관리자 일 때 재고없는 상품명 누르면 구매조회 뜨게 하기 -->
					<c:if test = "${ menu eq 'manage' && product.proTranCode ne '판매중' }" >
						<span class = "getPurchase" id = "${ product.proPurchase.tranNo }">
<%-- 							<a href="/purchase/getPurchase?tranNo=${ product.proPurchase.tranNo }"> --%>
							${ product.prodName }					
						</span>
					</c:if>
			</td>
			<td></td>
			<td align="left">${ product.price }</td>
			<td></td>
			<td align="left">
				<c:if test = "${ menu eq 'manage' }">${ product.regDate }</c:if>
				<c:if test = "${ menu eq 'search' }">${ product.prodDetail }</c:if>
			</td>
			<td></td>
			<td align="left">
			<c:if test = "${ menu eq 'manage' }">
				${ product.proTranCode }
				<c:if test = "${ product.proTranCode eq '구매완료' }">
					<span class = "updateTranCodeByProd" id = "${product.prodNo}">
<%-- 					<a href="/purchase/updateTranCodeByProd?prodNo=${ product.prodNo }&tranCode=2"> --%>
					<b>배송하기</b>
					</span>
				</c:if>
				<!-- 추가 기능 -->
				<!-- 추후 상품 삭제 플래그처리 하기 ★ -->
				<c:if test = "${ product.proTranCode eq '판매중' }">
					<span class = "deleteProduct" id = "${product.prodNo}">
<%-- 					<a href="/product/deleteProduct?prodNo=${ product.prodNo }"> --%>
					<b>상품삭제</b>
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
		<tr>	
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>	
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="menu" name="menu" value="${ menu }"/>
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include page="../common/pageNavigator.jsp"/>	
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>