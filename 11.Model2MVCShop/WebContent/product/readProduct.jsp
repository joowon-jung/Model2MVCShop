<!-- addProductView �� ������ �о Ȯ�� ������ ��ǰ����, �߰���� ������ addProductView�� �̵� -->
<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>��ǰ���</title>

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
			
			$("button:contains('�߰����')").on("click", function () {
				self.location = "../product/addProductView.jsp";
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
	       <h3 class="text-center">ADD PRODUCT</h3>
	    </div>
		
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
	  		<div class="col-md-12 text-center ">

	  			<button type="button" class="btn btn-outline-primary">�߰����</button>

	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">Ȯ��</button>
	  		</div>
		</div>
		
		<br/>
		
 	</div>

</body>
</html>
