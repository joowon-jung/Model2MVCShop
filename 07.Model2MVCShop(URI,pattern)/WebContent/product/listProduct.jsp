<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>${ menu eq 'manage' ? "상품 관리" : "상품 목록조회" }</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

//검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
function fncGetList(currentPage){
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
<form name="detailForm" action="/product/listProduct?menu=${ menu }" method="post">

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
			<input type="checkbox" name = "findby" value = "sale" onclick="fncGetList('1')" ${ search.findby eq 'sale' ? "checked" : ""}/><b>판매중인 상품</b>
			<input type="checkbox" name = "findby" value = "sold" onclick="fncGetList('1')" ${ search.findby eq 'sold' ? "checked" : ""}/><b>판매된 상품</b>
		</c:if>
		</td>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
			</select>
			<input type="text" name="searchKeyword" value = "${! empty search.searchKeyword ? search.searchKeyword : "" }"
						class="ct_input_g" style="width:200px; height:19px" />
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">검색</a>
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
					<c:if test = "${ product.proTranCode eq '판매중' }" >
						<a href="/product/getProduct?prodNo=${ product.prodNo }&menu=${ menu }">
					</c:if>
					<!-- 관리자 일 때 재고없는 상품명 누르면 구매조회 뜨게 하기 --> 
					<c:if test = "${ menu eq 'manage' && product.proTranCode ne '판매중' }" >
						<a href="/purchase/getPurchase?tranNo=${ product.proPurchase.tranNo }">
<%-- 						<a href="/purchase/getPurchase?tranNo=${ product.proPurchase.tranNo }&menu=${ menu }"> --%>
					</c:if>
					${ product.prodName }</a>
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
			<!-- 추후 상품 상태 수정하기 ★ -->
			<c:if test = "${ menu eq 'manage' }">
				${ product.proTranCode }
				<c:if test = "${ product.proTranCode eq '구매완료' }">
					<a href="/purchase/updateTranCodeByProd?prodNo=${ product.prodNo }&tranCode=2">배송하기</a>
				</c:if>
				<!-- 추가 기능 -->
				<c:if test = "${ product.proTranCode eq '판매중' }">
					<a href="/product/deleteProduct?prodNo=${ product.prodNo }">상품삭제</a>
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