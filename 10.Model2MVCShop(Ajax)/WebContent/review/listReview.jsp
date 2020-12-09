<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>REVIEW</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<!-- CDN(Content Delivery Network) ȣ��Ʈ ��� -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

function fncGetList(currentPage){
	$("#currentPage").val(currentPage);
	$("form").attr("method", "POST").attr("action", "/review/listReview").submit();	
}

$(function() {
	
	$("input:checkbox").on("click", function () {
		fncGetList(1);
	});
	
	$("td.ct_btn01:contains('�˻�')").on("click", function () {
		fncGetList(1);
	});
	
	$(".ct_list_pop td:nth-child(3) .getReview").on("click", function () {
		self.location = "/review/getReview?tranNo="+$(this).attr('id')+"&menu=all";
	});
		
});
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
<form name="detailForm" action="/review/listReview" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
							
						REVIEW 
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
		<td>
<%-- 			<input type="checkbox" name = "findby" value = "count" onclick="fncGetList('1')" ${ search.findby eq 'count' ? "checked" : ""}/><b>�α⸮���</b> --%>
			<input type="checkbox" name = "findby" value = "count" ${ search.findby eq 'count' ? "checked" : ""}/><b>�α⸮���</b>
			<input type="checkbox" name = "findby" value = "latest" ${ search.findby eq 'latest' ? "checked" : ""}/><b>�ֽż�</b>
			<input type="checkbox" name = "findby" value = "highGrade" ${ search.findby eq 'highGrade' ? "checked" : ""}/><b>����������</b>
			<input type="checkbox" name = "findby" value = "lowGrade" ${ search.findby eq 'lowGrade' ? "checked" : ""}/><b>����������</b>
		</td>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��</option>
				<!-- ���� �˻� ���� �����ϱ� -->
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>����</option>
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
<!-- 						<a href="javascript:fncGetList('1');"> -->
						�˻�
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
		<td colspan="13" >��ü ${ resultPage.totalCount } �Ǽ�, ���� ${ resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">���� </td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�ۼ���</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�ۼ���¥</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ȸ��</td>	
	</tr>
	<tr>
		<td colspan="13" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var = "i" value = "0" />
	<c:forEach var = "review" items = "${ list }">
		<c:set var = "i" value = "${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td> <!-- No : 1 2 3 ǥ�õǴ� �κ� --> 
			<td></td>
				<td align="left">
				<!-- ���� ���� -->
				<span class = "getReview" id = "${ review.reviewPurchase.tranNo }">
<%-- 				<a href="/review/getReview?tranNo=${ review.reviewPurchase.tranNo }&menu=all"> --%>
				<b>${ review.reviewProd.prodName }</b>
				</span>
				</td>
			<td></td>
			<td align="left">${ review.title }</td>
			<td></td>
			<td align="left">
			${ review.contents }
			</td>
			<td></td>
			<td align="left">
			${ review.writer.userId }
			</td>
			<td></td>
			<td align="left">
			${ review.writeDate }
			</td>
			<td></td>
			<td align="left">
			${ review.count }
			</td>
		</tr>
		<tr>	
		<td colspan="13" bgcolor="D6D7D6" height="1"></td>
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
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>