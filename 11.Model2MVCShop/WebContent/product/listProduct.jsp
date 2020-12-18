<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>${ menu eq 'manage' ? "��ǰ ����" : "��ǰ �����ȸ" }</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">

<!-- CDN(Content Delivery Network) ȣ��Ʈ ��� -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

//�˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�  
function fncGetList(currentPage){
	
	$("#currentPage").val(currentPage);
   	$("form").attr("method", "POST").attr("action", "/product/listProduct").submit();
   	
}

$(function() {
	
	$("input:checkbox[name='findby']").on("click", function () {
		fncGetList(1);
	});
	
	$("td.ct_btn01:contains('�˻�')").on("click", function () {
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
// 							//alert("�̸� : " + JSONData[i].prodName);
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
	
	$( "#tags" ).one("click" , function() { // one : Ŭ���� �ѹ��� �۵��ϵ��� 
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
							//alert("�̸� : " + JSONData[i].prodName);
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
							
						${ menu eq 'manage' ? "��ǰ ����" : "��ǰ �����ȸ" }
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
		<!-- ���� ���� | ���� & ������ �����Ͽ� ���� -->
<%-- 		<c:if test = "${ menu eq 'search' }">  --%>

<!-- 		<td> -->
<!-- 			<a href="javascript:fncGetList('1');"> -->
<!-- 			<input type="hidden" id="sorting" name="sorting" value="byPrice"/> -->
<!-- 			<b>���ݳ�����</b> -->
<!-- 			</a> -->
<!-- 		</td> -->
<!-- 		<td> -->
<!-- 			<a href="javascript:fncGetList('1');"> -->
			
<!-- 			<b>�ֽż�</b> -->
<!-- 			</a> -->
<!-- 		</td> -->
<%-- 		</c:if> --%>
		<td>
		<c:if test = "${ menu eq 'manage' }">
			<input type="checkbox" name = "findby" value = "sale" ${ search.findby eq 'sale' ? "checked" : ""}/><b>�Ǹ����� ��ǰ</b>
			<input type="checkbox" name = "findby" value = "sold" ${ search.findby eq 'sold' ? "checked" : ""}/><b>�Ǹŵ� ��ǰ</b>
		</c:if>
		</td>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
				<option value="1" ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>��ǰ����</option>
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
		<td colspan="11" >��ü ${ resultPage.totalCount } �Ǽ�, ���� ${ resultPage.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">
			<c:if test = "${ menu eq 'manage' }">�������</c:if>
			<c:if test = "${ menu eq 'search' }">������</c:if>
		</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var = "i" value = "0" />
	<c:forEach var = "product" items = "${ list }">
		<c:set var = "i" value = "${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td> <!-- No : 1 2 3 ǥ�õǴ� �κ� --> 
			<td></td>
			<td align="left">
					<c:if test = "${ product.proTranCode eq '�Ǹ���' || menu eq 'search' && product.proTranCode ne '�Ǹ���'}" >
						<span class = "getProduct" id = "${product.prodNo}">
							<!-- �ϳ��� �̾����ϱ� ���� �� �� ����! ���⼭�� list�� �����������ϱ� �ƴ°���!! ������ ȭ����� list�� ����Ǿ� �ִ� �� �𸣱⶧����
							list[0].��¼��.��¼���� �����ؾ��ϴµ� �׷��ԵǸ� �� ���� ��ǰ��ȣ�� ���ԵǴϱ� �׳� �ƿ� ��� ��°� �����Ű���!!!!! -->
<%-- 							<a href="/product/getProduct?prodNo=${ product.prodNo }&menu=${ menu }"> --%>
							${ product.prodName }
						</span>
					</c:if>
					<!-- ������ �� �� ������� ��ǰ�� ������ ������ȸ �߰� �ϱ� -->
					<c:if test = "${ menu eq 'manage' && product.proTranCode ne '�Ǹ���' }" >
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
				<c:if test = "${ product.proTranCode eq '���ſϷ�' }">
					<span class = "updateTranCodeByProd" id = "${product.prodNo}">
<%-- 					<a href="/purchase/updateTranCodeByProd?prodNo=${ product.prodNo }&tranCode=2"> --%>
					<b>����ϱ�</b>
					</span>
				</c:if>
				<!-- �߰� ��� -->
				<!-- ���� ��ǰ ���� �÷���ó�� �ϱ� �� -->
				<c:if test = "${ product.proTranCode eq '�Ǹ���' }">
					<span class = "deleteProduct" id = "${product.prodNo}">
<%-- 					<a href="/product/deleteProduct?prodNo=${ product.prodNo }"> --%>
					<b>��ǰ����</b>
					</span>
				</c:if>
			</c:if>
			<c:if test = "${ menu eq 'search' }">
				<c:if test = "${ product.proTranCode eq '�Ǹ���' }">
				�Ǹ���
				</c:if>
				<c:if test = "${ ! (product.proTranCode eq '�Ǹ���') }">
				�������
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
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>