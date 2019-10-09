<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
<c:if test = "${error!=null}">
<h1>${error}<h1>
	</c:if>
		<c:if test = "${error==null}">
<h3>Potential Duplicates</h3>
<ul>
	<c:forEach var="userInfo" items="${dupUsers}">
		<li>${userInfo}</li>
	</c:forEach>
</ul>
<h3>None Duplicates</h3>
<ul>
	<c:forEach var="userInfo" items="${noDupUsers}">
		<li>${userInfo}</li>
	</c:forEach>
</ul>
	</c:if>

</body>
</html>