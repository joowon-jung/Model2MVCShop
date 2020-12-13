<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

<title>��ǰ����ȸ</title>

	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 body >  div.container{ 
            margin-top: 50px;
        }
    </style>
	
	<!-- CDN(Content Delivery Network) ȣ��Ʈ ��� -->
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	
		$(function() {
			
			$("button:contains('Ȯ��')").on("click", function () {
				self.location = "/product/listProduct?menu=manage";
			});
			
			$("button:contains('BUY')").on("click", function () {
				self.location = "/purchase/addPurchase?prod_no=${ vo.prodNo }"
			});
			
			$("button:contains('����')").on("click", function () {
				history.go(-1); 
			});

		});
	
	</script>
	
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->	


	<div class="container">
	
		<div class="page-header">
	       <h1 class="text-center">PRODUCT INFO</h1>
	    </div>
	
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>�� ǰ �� ȣ</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.prodNo }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� ǰ ��</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.prodName }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� ǰ �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4"><img src="/images/uploadFiles/${ vo.fileName }" width="200" height="200"/></div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� ǰ �� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.prodDetail }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>�� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.manuDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.price }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ vo.regDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
	  		<!-- ��ǰ �����ϰ� �Ѿ���� �� �� ��ư ���̰� �� -->
	  		<c:if test = "${ menu eq 'ok' }" > 
	  			<button type="button" class="btn btn-outline-primary">Ȯ��</button>
	  		</c:if>
	  		<!-- ��ȸ�� & �����ڴ� ��ǰ ���� �Ұ��ϰ� �� -->
			<c:if test = "${ ! empty sessionScope.user && ! (sessionScope.user.role eq 'admin') }">
			<c:if test = "${ vo.proPurchase.tranNo eq '0' }" > <!-- �̹� ���ŵ� ��ǰ�̸� ���� �Ұ� -->
	  			<button type="button" class="btn btn-outline-primary">BUY</button>
	  		</c:if>
	  		</c:if>
	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">����</button>
	  		</div>
		</div>
		
		<br/>
		
 	</div>

</body>
</html>