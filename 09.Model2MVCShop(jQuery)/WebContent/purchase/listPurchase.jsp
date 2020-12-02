<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>구매 내역 조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetList(currentPage){
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/purchase/listPurchase" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매내역 조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="13">
		전체  ${ resultPage.totalCount } 건수,	현재 ${ resultPage.currentPage } 페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b">주문날짜</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">주문번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">제품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">상품평</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="13" bgcolor="808285" height="1"></td>
	</tr>

	<c:set var = "i" value = "0" />
	<c:forEach var = "purchase" items = "${ list }">
		<c:set var = "i" value = "${ i + 1 }"/>
		<tr class="ct_list_pop">
			<td align="center">
				${ purchase.orderDate }
			</td>
			<td></td>
			<td align="left">
				<a href="/purchase/getPurchase?tranNo=${ purchase.tranNo }">${ purchase.tranNo }</a>
				<!-- <a href="/getUser.do?userId=${ purchase.buyer.userId }">${ purchase.buyer.userId }</a> -->
			</td>
			<td></td>
			<td align="left">${ purchase.purchaseProd.prodName }</td>
			<td></td>
			<td align="left">${ purchase.purchaseProd.price }</td>
			<td></td>
					<td align="left">현재 ${ purchase.tranCode } 상태 입니다.
						<!-- 추가한 부분! 추후 수정 -->
					</td>
					<td></td>
					<td align="left">
						<c:if test = "${ purchase.tranCode eq '배송완료' && purchase.reviewNo==0 }">
							<a href="/review/addReview?prodNo=${ purchase.purchaseProd.prodNo }&tranNo=${ purchase.tranNo }">상품평 등록</a>
						</c:if>
						<c:if test = "${ purchase.tranCode eq '배송완료' && purchase.reviewNo!=0 }">
							<a href="/review/getReview?tranNo=${ purchase.tranNo }&menu=own">상품평 보기</a>
						</c:if>
					</td>
					<td></td>
					<td align="left">
					<c:if test = "${ purchase.tranCode eq '배송중' }">
						<a href="/purchase/updateTranCode?tranNo=${ purchase.tranNo }&tranCode=3">물건도착</a>
					</c:if>
				</td>
				</tr>
				<tr>
					<td colspan="13" bgcolor="D6D7D6" height="1"></td>
				</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
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