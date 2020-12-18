<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<!--  ///////////////////////// JSTL  ////////////////////////// -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.css" >
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.min.css" >

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>

</head>
<body>
		
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">MODEL2 MVC SHOP</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor03" aria-controls="navbarColor03" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarColor03">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">HOME
          <span class="sr-only">(current)</span>
        </a>
      </li>
      <c:if test="${ ! empty user }">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">User</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="#">����������ȸ</a>
          <c:if test="${sessionScope.user.role == 'admin'}">
          	<a class="dropdown-item" href="#">ȸ��������ȸ</a>
          </c:if>
        </div>
      </li>
      </c:if>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">PRODUCT</a>
        <div class="dropdown-menu">
           <c:if test="${sessionScope.user.role == 'admin'}">
         	<a class="dropdown-item" href="#">��ǰ���</a>
          	<a class="dropdown-item" href="#">��ǰ����</a>
         	 <div class="dropdown-divider"></div>
          </c:if>
          <a class="dropdown-item" href="#">��ǰ�˻�</a>
        </div>
      </li>
       <c:if test="${sessionScope.user.role == 'user'}">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">PURCHASE</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="#">�����̷���ȸ</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">�ֱ� �� ��ǰ</a>
        </div>
      </li>
      </c:if>
	<li class="nav-item">
        <a class="nav-link" href="#">REVIEW</a>
      </li>
    </ul>
    <ul class="nav mr-right">
    	
    	<c:if test="${ empty user }">
	    <li class="nav-item active">
        <a class="nav-link" href="#">SIGN UP</a>
      	</li>
	    <li class="nav-item active">
        <a class="nav-link" href="#">LOGIN</a>
      	</li>
      	</c:if>
      	<c:if test="${ ! empty user }">
      		<c:if test="${sessionScope.user.role == 'admin'}">
     			<!-- ���� ���� ���� -->
      			<span class="badge badge-primary">ADMIN</span>
      		</c:if>
      		<c:if test="${sessionScope.user.role == 'user'}">
      			<!-- ���� ���� ���� -->
      			<span class="badge badge-primary">USER</span>
      		</c:if>
      	<li class="nav-item active">
        <a class="nav-link" href="#">LOGOUT</a>
      	</li>
      	</c:if>
	</ul>
  </div>
</nav>

</body>	
   	
   	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
   	<script type="text/javascript">
   	
   		function history(){
			popWin = window.open("/history.jsp",
								"popWin",
		"left=300, top=200, width=300, height=200, marginwidth=0, marginheight=0, scrollbars=no, scrolling=no, menubar=no, resizable=no");
		}
   		
		//============= logout Event  ó�� =============	
		 $(function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('LOGOUT')").on("click" , function() {
				$(self.location).attr("href","/user/logout");
				//self.location = "/user/logout"
			}); 
			
		 	$("a:contains('LOGIN')").on("click" , function() {
				$(self.location).attr("href","/user/login");
				//self.location = "/user/logout"
			}); 
		 	
		 	$("a[href='#' ]:contains('SIGN UP')").on("click" , function() {
				self.location = "/user/addUser"
			});
		 	
		 });
		
		//============= ȸ��������ȸ Event  ó�� =============	
		 $(function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
		 	$("a:contains('ȸ��������ȸ')").on("click" , function() {
				//$(self.location).attr("href","/user/logout");
				self.location = "/user/listUser"
			}); 
		 });
		
		//=============  ����������ȸȸ Event  ó�� =============	
	 	$( "a:contains('����������ȸ')" ).on("click" , function() {
	 		//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$(self.location).attr("href","/user/getUser?userId=${sessionScope.user.userId}");
		});
		
		$("a:contains('��ǰ���')").on("click", function() {
			$(self.location).attr("href","../product/addProductView.jsp;");
		});
		
		$("a:contains('��ǰ����')").on("click", function() {
			$(self.location).attr("href","/product/listProduct?menu=manage");
		});
		
		$("a:contains('��ǰ�˻�')").on("click", function() {
			$(self.location).attr("href","/product/listProduct?menu=search");
		});
				
		$("a:contains('�����̷���ȸ')").on("click", function() {
			$(self.location).attr("href","/purchase/listPurchase");
		});
				
		$("a:contains('REVIEW')").on("click", function() {
			$(self.location).attr("href","/review/listReview");
		});
		
		$("a:contains('�ֱ� �� ��ǰ')").on("click", function() {
			history();
		});
		
		// ���� ȭ������ ���Բ� 
		$("a[href='#' ]:contains('MODEL2 MVC SHOP')").on("click" , function() {
			self.location = "/index.jsp"
		});
		
		$("a[href='#' ]:contains('HOME')").on("click" , function() {
			self.location = "/index.jsp"
		});
		
	</script>  