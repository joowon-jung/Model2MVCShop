<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>���� ���� ��ȸ</title>

<!-- ���� : http://getbootstrap.com/css/   ���� -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.css" >
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 div.container{ 
            margin-top: 50px;
        }
        
        .wrap{width:30%; text-align:center; margin:0px auto;}
    </style>

<script type="text/javascript">

function fncGetList(currentPage){ // �� �� - �˻������ ��� ���� �� ��? ���߿� �˻���� �߰��Ϸ��� ����
	
	$("#currentPage").val(currentPage);
	$("form").attr("method", "POST").attr("action", "/purchase/listPurchase").submit();
}

$(function() {
		
	$(".getPurchase").on("click", function () {
		self.location = "/purchase/getPurchase?tranNo="+$(this).attr('id');
	});
	
	$(".getProduct").on("click", function () { // ��ǰ�� ������ ���� �������� ��
		self.location = "/product/getProduct?prodNo="+$(this).attr('id')+"&menu=search";
	});
	
	$(".badge.badge-success").on("click", function () { // ���� ���
		self.location = "/review/addReview?prodNo="+$(this).attr('id')+"&tranNo="+$(this).attr('value');
	});
	
	$(".badge.badge-info").on("click", function () { // ���� ��ȸ
		self.location = "/review/getReview?tranNo="+$(this).attr('id')+"&menu=own";
	});
	
	$(".badge.badge-warning").on("click", function () { // ��ǰ ���� -> ��ۿϷ�
		self.location = "/purchase/updateTranCode?tranNo="+$(this).attr('id')+"&tranCode=3";
	});
	
	//==> LINK Event End User ���� ���ϼ� �ֵ��� 
	$( "td:nth-child(2)" ).css("color" , "black");
	
	//==> LINK Event End User ���� ���ϼ� �ֵ��� 
	$( "td:nth-child(3)" ).css("color" , "black");
	
});

</script>
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->
<form name="detailForm">

<!--  ȭ�鱸�� div Start /////////////////////////////////////-->
	<div class="container">
	
		<div class="page-header text-center">
	       <h1>PURCHASE LIST</h1>
	    </div>
	    
	    <!-- table ���� /////////////////////////////////////-->
	    <div class="row">
	    
		    <div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		<br>��ü  ${ resultPage.totalCount } �Ǽ�,	 ���� ${ resultPage.currentPage } ������
		    	</p>
		    </div>
	    	
	    	<!-- PageNavigation ���� ������ ���� ������ �κ� -->
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
	    	
		</div>
		<!-- table ���� /////////////////////////////////////-->
		
		
      <!--  table Start /////////////////////////////////////-->
      <table class="table table-hover">
      
        <thead>
   			 <tr>
      			<th scope="col">�ֹ���¥</th>
      			<th scope="col">�ֹ���ȣ</th>
      			<th scope="col">��ǰ</th>
      			<th scope="col">����</th>
      			<th scope="col">�������</th>
      			<th scope="col">REVIEW</th>
      			<th scope="col">���¼���</th>
    		</tr>
  		</thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		  <c:forEach var = "purchase" items = "${ list }">
			<c:set var = "i" value = "${ i + 1 }"/>
			<tr class="table-light">
			  <td align="left">${ purchase.orderDate }</td>
			  <td align="left">
			  	<span class = "getPurchase" id = "${ purchase.tranNo }">
					<b>${ purchase.tranNo }</b>
				</span>
			  </td>
			  <td align="left">
			  	<span class = "getProduct" id = "${ purchase.purchaseProd.prodNo }">
			  		<b>${ purchase.purchaseProd.prodName }</b>
			  		<br>
			  		<img src="/images/uploadFiles/${ purchase.purchaseProd.fileName }" width="100" height="100"/>
			   </span>
			  </td>
			  <td align="left">${ purchase.purchaseProd.price }</td>
			  <td align="left">${ purchase.tranCode }</td>
			  <td align="left">
			  	<c:if test = "${ purchase.tranCode eq '��ۿϷ�' && purchase.reviewNo==0 }">
			  		<span class = "badge badge-success" id = "${ purchase.purchaseProd.prodNo }"  value = "${ purchase.tranNo }">
			  		��ǰ�� ���
					</span>
				</c:if>
				<c:if test = "${ purchase.tranCode eq '��ۿϷ�' && purchase.reviewNo!=0 }">
					<span class = "badge badge-info" id = "${ purchase.tranNo }">
					��ǰ�� ����
					</span>
				</c:if>
			  </td>
			  <td align="left">
			  	<c:if test = "${ purchase.tranCode eq '�����' }">
			  		<span class = "badge badge-warning" id = "${ purchase.tranNo }">
			  		��ǰ ����
			  		</span>
			  	</c:if>
			  </td>
			</tr>
          </c:forEach>
        
        </tbody>
      
      </table>
	  <!--  table End /////////////////////////////////////-->
	  
	  	<div class="wrap">
	   	<!-- PageNavigation Start... -->
		<jsp:include page="../common/pageNavigator_new.jsp"/>
		<!-- PageNavigation End... -->
		</div>

 	</div>
 	<!--  ȭ�鱸�� div End /////////////////////////////////////-->
	</form>
</body>
</html>