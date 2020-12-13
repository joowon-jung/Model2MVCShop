<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>


<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<!-- ���� : http://getbootstrap.com/css/   ���� -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.css" >
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.5.3/lux/bootstrap.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
       body > div.container{
            margin-top:  50px;
        }

    </style>
    
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
	<script type="text/javascript">
	
		//============= "����"  Event ���� =============
		 $(function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$( "button.btn-outline-primary" ).on("click" , function() {
				fncAddUser();
			});
		});	
		
		
		//============= "���"  Event ó�� ��  ���� =============
		$(function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$("a[href='#' ]").on("click" , function() {
				$("form")[0].reset();
			});
		});	
	
		
		function fncAddUser() {
			
			var id=$("input[name='userId']").val();
			var pw=$("input[name='password']").val();
			var pw_confirm=$("input[name='password2']").val();
			var name=$("input[name='userName']").val();
			
			
			if(id == null || id.length <1){
				alert("���̵�� �ݵ�� �Է��ϼž� �մϴ�.");
				return;
			}
			if(pw == null || pw.length <1){
				alert("�н������  �ݵ�� �Է��ϼž� �մϴ�.");
				return;
			}
			if(pw_confirm == null || pw_confirm.length <1){
				alert("�н����� Ȯ����  �ݵ�� �Է��ϼž� �մϴ�.");
				return;
			}
			if(name == null || name.length <1){
				alert("�̸���  �ݵ�� �Է��ϼž� �մϴ�.");
				return;
			}
			
			if( pw != pw_confirm ) {				
				alert("��й�ȣ Ȯ���� ��ġ���� �ʽ��ϴ�.");
				$("input:text[name='password2']").focus();
				return;
			}
				
			var value = "";	
			if( $("input:text[name='phone2']").val() != ""  &&  $("input:text[name='phone3']").val() != "") {
				var value = $("option:selected").val() + "-" 
									+ $("input[name='phone2']").val() + "-" 
									+ $("input[name='phone3']").val();
			}

			$("input:hidden[name='phone']").val( value );
			
			$("form").attr("method" , "POST").attr("action" , "/user/addUser").submit();
		}
		

		//==>"�̸���" ��ȿ��Check  Event ó�� �� ����
		 $(function() {
			 
			 $("input[name='email']").on("change" , function() {
				
				 var email=$("input[name='email']").val();
			    
				 if(email != "" && (email.indexOf('@') < 1 || email.indexOf('.') == -1) ){
			    	alert("�̸��� ������ �ƴմϴ�.");
			     }
			});
			 
		});	
		
		
	   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	   //==> �ֹι�ȣ ��ȿ�� check �� ����������....
		function checkSsn() {
			var ssn1, ssn2; 
			var nByear, nTyear; 
			var today; 
	
			ssn = document.detailForm.ssn.value;
			// ��ȿ�� �ֹι�ȣ ������ ��츸 ���� ��� ����, PortalJuminCheck �Լ��� CommonScript.js �� ���� �ֹι�ȣ üũ �Լ��� 
			if(!PortalJuminCheck(ssn)) {
				alert("�߸��� �ֹι�ȣ�Դϴ�.");
				return false;
			}
		}
	
		function PortalJuminCheck(fieldValue){
		    var pattern = /^([0-9]{6})-?([0-9]{7})$/; 
			var num = fieldValue;
		    if (!pattern.test(num)) return false; 
		    num = RegExp.$1 + RegExp.$2;
	
			var sum = 0;
			var last = num.charCodeAt(12) - 0x30;
			var bases = "234567892345";
			for (var i=0; i<12; i++) {
				if (isNaN(num.substring(i,i+1))) return false;
				sum += (num.charCodeAt(i) - 0x30) * (bases.charCodeAt(i) - 0x30);
			}
			var mod = sum % 11;
			return ((11 - mod) % 10 == last) ? true : false;
		}
		 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		 
		//==>"ID�ߺ�Ȯ��" Event ó�� �� ����
		 $(function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			 $("button.btn.btn-info").on("click" , function() {
				popWin 
				= window.open("/user/checkDuplication.jsp",
											"popWin", 
											"left=300,top=200,width=780,height=130,marginwidth=0,marginheight=0,"+
											"scrollbars=no,scrolling=no,menubar=no,resizable=no");
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
	
		<h1 class="text-center">SIGN UP</h1>
		
		<!-- form Start /////////////////////////////////////-->
		<form class="form">
		
		  <label for="userId" class="col-form-label">���̵�</label>
		  <div class="text-right">
		  	  <div class="row">
		  	   <div class="col-md-9">
		      <input type="text" class="form-control" id="userId" name="userId" placeholder="�Է� �� �ߺ�Ȯ��" width="350" readonly>
		      </div>
		       <div class="col-md-3 text-right">
		      <button type="button" class="btn btn-info">�� �� Ȯ ��</button>
		      </div>
		      </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="password" class="col-form-label">��й�ȣ</label>
		      <input type="password" class="form-control" id="password" name="password" placeholder="��й�ȣ">
		  </div>
		  
		  <div class="form-group">
		    <label for="password2" class="col-form-label">��й�ȣ Ȯ��</label>
		      <input type="password" class="form-control" id="password2" name="password2" placeholder="��й�ȣ Ȯ��">
		  </div>
		  
		  <div class="form-group">
		    <label for="userName" class="col-form-label">�̸�</label>
		      <input type="text" class="form-control" id="userName" name="userName" placeholder="ȸ���̸�">
		  </div>
		  
		  <div class="form-group">
		    <label for="ssn" class="col-form-label">�ֹι�ȣ</label>
		      <input type="password" class="form-control" id="ssn" name="ssn" placeholder="�ֹι�ȣ">
		      <span id="helpBlock" class="help-block">
		      	 <strong class="text-danger">" -  " ���� 13�ڸ� �Է�</strong>
		      </span>
		  </div>
		  
		  <div class="form-group">
		    <label for="ssn" class="col-form-label">�ּ�</label>
		      <input type="text" class="form-control" id="addr" name="addr" placeholder="�ּ�">
		  </div>
		  
		  <div class="form-group">
		    <label for="ssn" class="col-form-label">�޴���ȭ��ȣ</label>
		     <div class="row">
		  	   <div class="col-md-4">
		     	 <select class="form-control" name="phone1" id="phone1">
				  	<option value="010" >010</option>
					<option value="011" >011</option>
					<option value="016" >016</option>
					<option value="018" >018</option>
					<option value="019" >019</option>
				</select>
				</div>
				<div class="col-md-4">
		      		<input type="text" class="form-control" id="phone2" name="phone2" placeholder="��ȣ">
		      	</div>
		      	<div class="col-md-4">
		      		<input type="text" class="form-control" id="phone3" name="phone3" placeholder="��ȣ">
		      </div>
			</div>
		    <input type="hidden" name="phone"  />
		  </div>
		  
		   <div class="form-group">
		    <label for="ssn" class="col-form-label">�̸���</label>
		      <input type="text" class="form-control" id="email" name="email" placeholder="email@example.com">
		  </div>
		  
		  <div class="form-group">
					    <div class="text-center">
					      <button type="button" class="btn btn-outline-primary">����</button>
					      &nbsp;&nbsp;&nbsp;&nbsp;
					      <a class="btn btn-outline-secondary" href="#" role="button">���</a>
					    </div>
					  </div>
		</form>
		<!-- form Start /////////////////////////////////////-->
		
 	</div>
	<!--  ȭ�鱸�� div end /////////////////////////////////////-->
	
	
</body>

</html>