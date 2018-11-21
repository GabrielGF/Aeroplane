<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
 <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
 <!--  <link href="${context}/css">  -->
</head>
<body>

		<form:form action="/formularioTripulante" method="POST" modelAttribute="tripulante">
		 
		  	<tr>
				<td>Jefe de cabina </td>
				<td>
				<form:radiobutton path="jefeDeCabina" value="true" />Si
				<form:radiobutton path="jefeDeCabina" value="false" />No</td>
			</tr>
		  
		
		<form:input path="nombre" type="text"/>
		
		<button class="btn btn-lg btn-primary" Type="Submit"/>Aceptar</button>
		
		</form:form>




</body>
</html>