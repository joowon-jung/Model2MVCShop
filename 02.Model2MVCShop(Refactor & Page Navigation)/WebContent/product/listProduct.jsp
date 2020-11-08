<%@ page contentType="text/html; charset=euc-kr" %>

<%@ page import="java.util.*"  %>

<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
// ListProductAction 에서 request에 담아 온 것을 받음 => 형변환 해서! (그냥 꺼내면 Object 타입)
List<Product> list= (List<Product>)request.getAttribute("list");
Page resultPage=(Page)request.getAttribute("resultPage");
	
Search searchVO = (Search)request.getAttribute("search");

//==> null 을 ""(nullString)으로 변경
// searchCondition이나 searchkeyword가 null이면 null.어쩌고 해서 exception 발생하니까 메소드 사용해준 것
String searchCondition = CommonUtil.null2str(searchVO.getSearchCondition());
String searchKeyword = CommonUtil.null2str(searchVO.getSearchKeyword());

String menu = (String) request.getAttribute("menu"); // manage(판매상품관리) & search(상품검색) 나누기 위함
%>
<html>
<head>
<% String title = menu.equals("manage") ? "상품 관리" : "상품 목록조회"; %>
<title><%= title %></title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
//검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
function fncGetProductList(currentPage){
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
<form name="detailForm" action="/listProduct.do?menu=<%=menu%>" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
							
						<%= title %>
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>상품번호</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>상품명</option>
				<option value="2" <%= (searchCondition.equals("2") ? "selected" : "")%>>상품가격</option>
			</select>
			<input type="text" name="searchKeyword" value = "<%= searchKeyword %>"
						class="ct_input_g" style="width:200px; height:19px" />
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 <%= resultPage.getTotalCount() %> 건수, 현재 <%=resultPage.getCurrentPage() %> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%
		for (int i = 0; i < list.size(); i++) { // for 문 돌려서 한줄씩 찾음
			Product vo = (Product) list.get(i);
	%>
	<tr class="ct_list_pop">
		<td align="center"><%= i + 1 %></td> <!-- No : 3 2 1 표시되는 부분 --> 
		<td></td>
				
				<td align="left">
				<% if (vo.getProTranCode().equals("판매중")) { %>
				<a href="/getProduct.do?prodNo=<%=vo.getProdNo() %>&menu=<%=menu%>">
				<% } %>
				<%= vo.getProdName() %></a>
				</td>
		<td></td>
		<td align="left"><%= vo.getPrice() %></td>
		<td></td>
		<td align="left"><%= vo.getRegDate() %></td>
		<td></td>
		<td align="left">
		
		<!-- 추후 상품 상태 수정하기 ★ -->
		
			<% if (menu.equals("manage")) { %>
				<%= vo.getProTranCode() %>
				<% if (vo.getProTranCode().equals("구매완료")) { %>
					<a href="/updateTranCodeByProd.do?prodNo=<%=vo.getProdNo()%>&tranCode=2">배송하기</a>
				<% } %>
			<% } else if (menu.equals("search")) { %>
				<% if (! (vo.getProTranCode().equals("판매중"))) { %>
					재고없음
				<% } else { %>
					판매중
				<% } %>
			<% } %>
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	<% } %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
				<!-- 현재 페이지가 <= 5 일 때는 이전버튼이 눌리지 않음 -->
					◀ 이전
			<% }else{ %>
					<!-- 현재 페이지가 5보다 크면 이전버튼이 눌리게 함 -->
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage(); i<= resultPage.getEndUnitPage() ;i++){	%>
					<!-- 1 2 3 4 5 이런 페이지들에 링크 걸어놓은 것 -->
					<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<!-- 화면에 보여지는 페이지 끝 번호가 전체 페이지와 같을 때  -->
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
			<!-- 화면에 보여지는 페이지 끝 번호가 전체 페이지보다 작을 때 버튼이 눌리게 함 -->
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
			<% } %>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>