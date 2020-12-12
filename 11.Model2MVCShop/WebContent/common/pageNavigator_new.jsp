<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		 
	<nav>
		<ul class="pagination pagination-lg">
			<!--  <<== 謝難 nav -->
			<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
				<li class="page-item disabled">
			</c:if>
			<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
				<li class="page-item">
			</c:if>
			<a class="page-link" href="javascript:fncGetList('${ resultPage.currentPage-1}')">&laquo;</a></li>

			<!--  醞懈  -->
			<c:forEach var="i" begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
			
				<c:if test="${ resultPage.currentPage == i }">
					<!--  ⑷營 page 陛腦鑒唳辦 : active -->
					<li class="page-item active">
				    	<a class="page-link" href="javascript:fncGetList('${ i }');">${ i }<span class="sr-only">(current)</span></a>
				    </li>
				</c:if>

				<c:if test="${ resultPage.currentPage != i}">
					<li class="page-item">
						<a class="page-link" href="javascript:fncGetList('${ i }');">${ i }</a>
					</li>
				</c:if>
				
			</c:forEach>

			<!--  辦難 nav==>> -->
			<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
				<li class="page-item disabled">
			</c:if>
			<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
				<li class="page-item">
			</c:if>
			 <a class="page-link" href="javascript:fncGetList('${resultPage.endUnitPage+1}')" aria-label="Next">&raquo;</a></li>
		</ul>
	</nav>