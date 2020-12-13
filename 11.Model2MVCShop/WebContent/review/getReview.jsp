<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 div.container{ 
            margin-top: 50px;
        }
    </style>


<script type="text/javascript" src="../javascript/calendar.js"></script>
<!-- CDN(Content Delivery Network) ȣ��Ʈ ��� -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

$(function() {
	
	$("button:contains('����')").on("click", function () {
		self.location = "/review/updateReview?prodNo=${ review.reviewProd.prodNo }&tranNo=${ review.reviewPurchase.tranNo }";
	});
	
	$("button:contains('Ȯ��')").on("click", function () {
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
	       <h1 class="text-center">REVIEW</h1>
	    </div>
	
		<input type="hidden" name="prodNo" value="${ product.prodNo }" />
		<input type="hidden" name="tranNo" value="${ purchase.tranNo }" />
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2"><strong>�� ǰ ��</strong></div>
			<div class="col-xs-8 col-md-4">${ review.reviewProd.prodName }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� ǰ �� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ review.reviewProd.prodDetail }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ review.reviewProd.price }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� �� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ review.writer.userId }</div>
			<input type="hidden" name="buyerId" value="${ review.writer.userId }" />
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� �� �� ¥</strong></div>
			<div class="col-xs-8 col-md-4">${ review.reviewPurchase.orderDate }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ review.title }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-xs-4 col-md-2 "><strong>�� ��</strong></div>
			<div class="col-xs-8 col-md-4">${ review.contents }</div>
		</div>
		
		<hr/>
		
		<div class="row">
	  		<div class="col-md-12 text-center ">
	  			<c:if test = "${ review.writer.userId eq sessionScope.user.userId }">
	  				<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target=".modal">����</button>	 
	  			</c:if>
	  			&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-secondary">Ȯ��</button>
	  		</div>
		</div>
		
		
 	</div>

</body>
</html>