<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>���Ż���ȸ</title>

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
			
			$("button:contains('����')").on("click", function () {
				self.location = "/purchase/updatePurchase?tranNo=${ purchase.tranNo }";
			});
			
			$("button:contains('Ȯ��')").on("click", function () {
				history.go(-1);
			});

		});
	
	</script>

</head>

<body bgcolor="#ffffff" text="#000000">

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->	

<div class="container">
	
		<div class="page-header">
	       <h1 class="text-center">PURCHASE INFO</h1>
	    </div>
	
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>�� ǰ �� ȣ</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.purchaseProd.prodNo }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.buyer.userId }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ fn:trim(purchase.paymentOption) eq '1' ? "���ݱ���" : "�ſ뱸��" }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>�� �� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.receiverName }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� �� �� ó</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.receiverPhone }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.divyAddr }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� û �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.divyRequest }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.divyDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ purchase.orderDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
				<!-- �������̰ų� �����, ��ۿϷ� �����϶� ���� �Ұ� -->
				<c:if test = "${ ! (sessionScope.user.role == 'admin') }">
					<c:if test = "${ ! (purchase.tranCode eq '�����' || purchase.tranCode eq '��ۿϷ�') }">
	  					<button type="button" class="btn btn-outline-primary">����</button>
					</c:if>
				</c:if>
	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">Ȯ��</button>
	  		</div>
		</div>
		
		<br/>
		
 	</div>

</body>
</html>