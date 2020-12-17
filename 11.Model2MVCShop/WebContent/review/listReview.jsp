<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>REVIEW</title>

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

function fncGetList(currentPage){
	$("#currentPage").val(currentPage);
	$("form").attr("method", "POST").attr("action", "/review/listReview").submit();	
}

$(function() {
	
	$("input:checkbox").on("click", function () {
		fncGetList(1);
	});
	
	$("button:contains('�˻�')").on("click", function () {
		fncGetList(1);
	});
	
	$(".getReview").on("click", function () {
		self.location = "/review/getReview?tranNo="+$(this).attr('id')+"&menu=all";
	});
		
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
	       <h1>REVIEW LIST</h1>
	    </div>
	    
	    <br><br>
	    <!-- table ���� �˻� Start /////////////////////////////////////-->
	    <div class="row">
	    
		    <div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		��ü ${ resultPage.totalCount } �Ǽ�, ���� ${ resultPage.currentPage} ������
		    	</p>
				<input type="checkbox" name = "findby" value = "count" ${ search.findby eq 'count' ? "checked" : ""}/><b> �α⸮���</b>
				<input type="checkbox" name = "findby" value = "latest" ${ search.findby eq 'latest' ? "checked" : ""}/><b> �ֽż�</b>
				<input type="checkbox" name = "findby" value = "highGrade" ${ search.findby eq 'highGrade' ? "checked" : ""}/><b> ����������</b>
				<input type="checkbox" name = "findby" value = "lowGrade" ${ search.findby eq 'lowGrade' ? "checked" : ""}/><b> ����������</b>
		    </div>
		   
		    <div class="col-md-6 text-right">
		    	<div class="row">
			      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <div class="col-md-4">
				    <select class="form-control" name="searchCondition" >
						<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��</option>
						<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>����</option>
					</select>
				  </div>
				  
				  <div class="col-md-5">
				    <label class="sr-only" for="searchKeyword">�˻���</label>
				    <input type="text" id="tags" class="form-control" name="searchKeyword"  placeholder="�˻���"
				    			 value="${! empty search.searchKeyword ? search.searchKeyword : '' }"  >
				  </div>
				  
				   <button type="button" class="btn btn-outline-primary">�˻�</button>
				  
				  <!-- PageNavigation ���� ������ ���� ������ �κ� -->
				  <input type="hidden" id="currentPage" name="currentPage" value=""/>
	    		</div>
	    	</div>
	    	
		</div>
		<!-- table ���� �˻� Start /////////////////////////////////////-->
		
		
      <!--  table Start /////////////////////////////////////-->
      <table class="table table-hover">
      
        <thead>
   			 <tr>
      			<th align="center" scope="col">No</th>
      			<th scope="col">��ǰ��</th>
      			<th scope="col">����</th>
      			<th scope="col">����</th>
      			<th scope="col">�ۼ���</th>
      			<th scope="col">�ۼ���¥</th>
      			<th scope="col">��ȸ��</th>
    		</tr>
  		</thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		  <c:forEach var = "review" items = "${ list }">
			<c:set var="i" value="${ i+1 }" />
			<tr class="table-light">
			  <td align="left">${ i }</td>
			  <td align="left">
			  	<span class = "getReview" id = "${ review.reviewPurchase.tranNo }">
			  		<b>${ review.reviewProd.prodName }</b>
			  	</span>
			  </td>
			  <td align="left">${ review.title }</td>
			  <td align="left">${ review.contents }</td>
			  <td align="left">${ review.writer.userId }</td>
			  <td align="left">${ review.writeDate }</td>
			  <td align="left">${ review.count }</td>
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