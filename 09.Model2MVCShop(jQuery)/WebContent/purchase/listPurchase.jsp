<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>���� ���� ��ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<!-- CDN(Content Delivery Network) ȣ��Ʈ ��� -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

function fncGetList(currentPage){ // �� �� - �˻������ ��� ���� �� ��? ���߿� �˻���� �߰��Ϸ��� �ʿ�
	
	$("#currentPage").val(currentPage);
	$("form").attr("method", "POST").attr("action", "/purchase/listPurchase").submit();
}

$(function() {
		
	$(".ct_list_pop td:nth-child(3) .getPurchase").on("click", function () {
		self.location = "/purchase/getPurchase?tranNo="+$(this).attr('id');
	});
	
	$(".ct_list_pop td:nth-child(11) .addReview").on("click", function () {
		self.location = "/review/addReview?prodNo="+$(this).attr('id')+"&tranNo="+$(this).attr('value');
	});
	
	$(".ct_list_pop td:nth-child(11) .getReview").on("click", function () {
		self.location = "/review/getReview?tranNo="+$(this).attr('id')+"&menu=own";
	});
	
	$(".ct_list_pop td:nth-child(13) .updateTranCode").on("click", function () {
		self.location = "/purchase/updateTranCode?tranNo="+$(this).attr('id')+"&tranCode=3";
	});
	
});

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm">

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
				<span class = "getPurchase" id = "${ purchase.tranNo }">
<%-- 				<a href="/purchase/getPurchase?tranNo=${ purchase.tranNo }"> --%>
				${ purchase.tranNo }
				</span>
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
							<span class = "addReview" id = "${ purchase.purchaseProd.prodNo }"  value = "${ purchase.tranNo }">
<%-- 							<a href="/review/addReview?prodNo=${ purchase.purchaseProd.prodNo }&tranNo=${ purchase.tranNo }"> --%>
							<b>��ǰ�� ���</b>
							</span>
						</c:if>
						<c:if test = "${ purchase.tranCode eq '��ۿϷ�' && purchase.reviewNo!=0 }">
							<span class = "getReview" id = "${ purchase.tranNo }">
<%-- 							<a href="/review/getReview?tranNo=${ purchase.tranNo }&menu=own"> --%>
							<b>��ǰ�� ����</b>
							</span>
						</c:if>
					</td>
					<td></td>
					<td align="left">
					<c:if test = "${ purchase.tranCode eq '�����' }">
						<span class = "updateTranCode" id = "${ purchase.tranNo }">
<%-- 						<a href="/purchase/updateTranCode?tranNo=${ purchase.tranNo }&tranCode=3"> --%>
						<b>���ǵ���</b>
						</span>
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