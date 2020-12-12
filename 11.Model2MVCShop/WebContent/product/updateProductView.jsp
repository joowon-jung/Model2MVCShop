<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>��ǰ��������</title>

	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 body >  div.container{ 
            margin-top: 50px;
        }
    </style>

<!-- CDN(Content Delivery Network) ȣ��Ʈ ��� -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../javascript/calendar.js"></script>

<script type="text/javascript">

function fncAddProduct(){
	
	//Form ��ȿ�� ����
	 var name = $("input[name='prodName']").val();
	 var detail = $("input[name='prodDetail']").val();
	 var manuDate = $("input[name='manuDate']").val();
	 var price = $("input[name='price']").val();

	if(name == null || name.length<1){
		alert("��ǰ���� �ݵ�� �Է��Ͽ��� �մϴ�.");
		return;
	}
	if(detail == null || detail.length<1){
		alert("��ǰ�������� �ݵ�� �Է��Ͽ��� �մϴ�.");
		return;
	}
	if(manuDate == null || manuDate.length<1){
		alert("�������ڴ� �ݵ�� �Է��ϼž� �մϴ�.");
		return;
	}
	if(price == null || price.length<1){
		alert("������ �ݵ�� �Է��ϼž� �մϴ�.");
		return;
	}
		
	$("form").attr("method", "POST").attr("action", "/product/updateProduct").submit();
}

$(function() {
	
	$("button:contains('����')").on("click", function() {
		fncAddProduct();
	});
	
	$("button:contains('���')").on("click", function() {
		history.go(-1);
	});
	
	$("img[src='../images/ct_icon_date.gif']").on("click", function() {
		show_calendar('document.detailForm.manuDate', $("input[name='manuDate']").val());
		//$("input[name='manuDate']").text().replace('-', '');
	});
});

</script>
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->	


<!--  ȭ�鱸�� div Start /////////////////////////////////////-->
<div class="container">

<h1 class="text-center">UPDATE PRODUCT</h1>
			
<form name="detailForm" enctype="multipart/form-data">

<input type="hidden" name="prodNo" value="${ vo.prodNo }"/> <!-- ���⼭ prodNo�� �������ְ� ����! -->
<input type="hidden" name="menu" value="ok"/> <!-- ���⼭ menu�� �������ְ� ����! -->

		<div class="form-group">
  			<label for="prodName" class="col-form-label">��ǰ��</label>
  			<input type="text" class="form-control" name="prodName" id="prodName" value="${ vo.prodName }">
		</div>	
		
		<div class="form-group">
  			<label for="prodDetail" class="col-form-label">��ǰ ������</label>
  			<input type="text" class="form-control" name="prodDetail" id="prodDetail" value="${ vo.prodDetail }">
		</div>	
		
		<div class="form-group">
  			<label for="manuDate" class="col-form-label">��������</label>
  			<img src="../images/ct_icon_date.gif" width="20" height="20" />
  			<input type="text" class="form-control" name="manuDate" id="manuDate" value="${ vo.manuDate }" readonly>
		</div>	
		
		<div class="form-group">
  			<label for="price" class="col-form-label">����</label>
  			<input type="text" class="form-control" placeholder="��" name="price" id="price" value="${ vo.price }" >
		</div>	
		
	<!-- ���� ���� ���� -->	
    <div class="form-group">
      <label for="exampleInputFile">��ǰ �̹���</label>
      <input multiple="multiple" type="file" class="form-control-file" name="uploadFileName" id="uploadFileName" value = "${ vo.fileName }">
    </div>
	
	<div class="form-group">
		<div class="text-center">
		<button type="button" class="btn btn-outline-primary">����</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-outline-secondary">���</button>
		</div>
	</div>

</form>
</div>

</body>
</html>