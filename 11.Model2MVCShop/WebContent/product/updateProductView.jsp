<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>상품정보수정</title>

	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 body >  div.container{ 
            margin-top: 50px;
        }
    </style>

<!-- CDN(Content Delivery Network) 호스트 사용 -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../javascript/calendar.js"></script>

<script type="text/javascript">

function fncAddProduct(){
	
	//Form 유효성 검증
	 var name = $("input[name='prodName']").val();
	 var detail = $("input[name='prodDetail']").val();
	 var manuDate = $("input[name='manuDate']").val();
	 var price = $("input[name='price']").val();

	if(name == null || name.length<1){
		alert("상품명은 반드시 입력하여야 합니다.");
		return;
	}
	if(detail == null || detail.length<1){
		alert("상품상세정보는 반드시 입력하여야 합니다.");
		return;
	}
	if(manuDate == null || manuDate.length<1){
		alert("제조일자는 반드시 입력하셔야 합니다.");
		return;
	}
	if(price == null || price.length<1){
		alert("가격은 반드시 입력하셔야 합니다.");
		return;
	}
		
	$("form").attr("method", "POST").attr("action", "/product/updateProduct").submit();
}

$(function() {
	
	$("button:contains('수정')").on("click", function() {
		fncAddProduct();
	});
	
	$("button:contains('취소')").on("click", function() {
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


<!--  화면구성 div Start /////////////////////////////////////-->
<div class="container">

<h1 class="text-center">UPDATE PRODUCT</h1>
			
<form name="detailForm" enctype="multipart/form-data">

<input type="hidden" name="prodNo" value="${ vo.prodNo }"/> <!-- 여기서 prodNo를 지정해주고 있음! -->
<input type="hidden" name="menu" value="ok"/> <!-- 여기서 menu를 지정해주고 있음! -->

		<div class="form-group">
  			<label for="prodName" class="col-form-label">상품명</label>
  			<input type="text" class="form-control" name="prodName" id="prodName" value="${ vo.prodName }">
		</div>	
		
		<div class="form-group">
  			<label for="prodDetail" class="col-form-label">상품 상세정보</label>
  			<input type="text" class="form-control" name="prodDetail" id="prodDetail" value="${ vo.prodDetail }">
		</div>	
		
		<div class="form-group">
  			<label for="manuDate" class="col-form-label">제조일자</label>
  			<img src="../images/ct_icon_date.gif" width="20" height="20" />
  			<input type="text" class="form-control" name="manuDate" id="manuDate" value="${ vo.manuDate }" readonly>
		</div>	
		
		<div class="form-group">
  			<label for="price" class="col-form-label">가격</label>
  			<input type="text" class="form-control" placeholder="원" name="price" id="price" value="${ vo.price }" >
		</div>	
		
	<!-- 추후 사진 수정 -->	
    <div class="form-group">
      <label for="exampleInputFile">상품 이미지</label>
      <input multiple="multiple" type="file" class="form-control-file" name="uploadFileName" id="uploadFileName" value = "${ vo.fileName }">
    </div>
	
	<div class="form-group">
		<div class="text-center">
		<button type="button" class="btn btn-outline-primary">수정</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="btn btn-outline-secondary">취소</button>
		</div>
	</div>

</form>
</div>

</body>
</html>