<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>���� ���� ��ȸ</title>

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
					<td width="93%" class="ct_ttl01">���ų��� ��ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="13">
		��ü  ${ resultPage.totalCount } �Ǽ�,	���� ${ resultPage.currentPage } ������
		</td>
	</tr>
	<tr>
		<td class="ct_list_b">�ֹ���¥</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�ֹ���ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��������</td>
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
					<td align="left">���� ${ purchase.tranCode } ���� �Դϴ�.
						<!-- �߰��� �κ�! ���� ���� -->
					</td>
					<td></td>
					<td align="left">
						<c:if test = "${ purchase.tranCode eq '��ۿϷ�' && purchase.reviewNo==0 }">
							<a href="/review/addReview?prodNo=${ purchase.purchaseProd.prodNo }&tranNo=${ purchase.tranNo }">��ǰ�� ���</a>
						</c:if>
						<c:if test = "${ purchase.tranCode eq '��ۿϷ�' && purchase.reviewNo!=0 }">
							<a href="/review/getReview?tranNo=${ purchase.tranNo }&menu=own">��ǰ�� ����</a>
						</c:if>
					</td>
					<td></td>
					<td align="left">
					<c:if test = "${ purchase.tranCode eq '�����' }">
						<a href="/purchase/updateTranCode?tranNo=${ purchase.tranNo }&tranCode=3">���ǵ���</a>
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

<!--  ������ Navigator �� -->
</form>

</div>

</body>
</html>