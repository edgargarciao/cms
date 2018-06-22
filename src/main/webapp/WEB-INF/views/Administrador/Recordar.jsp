<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Administrador - Login</title>
<meta name="description" content="Sufee Admin - HTML5 Admin Template">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="apple-touch-icon" href="apple-icon.png">
<link rel="shortcut icon" href="resources/images/favicon.ico">

<link rel="stylesheet" href="resources/assets/css/normalize.css">
<link rel="stylesheet" href="resources/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/assets/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/assets/css/themify-icons.css">
<link rel="stylesheet" href="resources/assets/css/flag-icon.min.css">
<link rel="stylesheet" href="resources/assets/css/cs-skin-elastic.css">
<!-- <link rel="stylesheet" href="resources/assets/css/bootstrap-select.less"> -->
<link rel="stylesheet" href="resources/assets/scss/style.css">

<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800'
	rel='stylesheet' type='text/css'>

</head>


<body class="bg-white">

	<%
	  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	  response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	%>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<div class="sufee-login d-flex align-content-center flex-wrap">
		<div class="container">
			<div class="login-content">
				<div class="login-logo">
					<a href="${contextPath}/"> <img class="align-content"
						src="resources/images/logo.png" alt="">
					</a>
				</div>


				<c:if test="${not empty wrong}">

					<div
						class="sufee-alert alert with-close alert-danger alert-dismissible fade show">
						<span class="badge badge-pill badge-danger">Error</span>
						<c:out value='${wrong}' />
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>


				</c:if>


				<div class="login-form">
					<!-- <form> -->
					<form:form action="recordarContraseña" method="post"
						modelAttribute="login">
						<div class="form-group">
							<label>Correo institucional</label>
							<form:input path="correoInstitucional" type="email"
								class="form-control" placeholder="anamarianagh@ufps.edu.co"
								required="true" />
						</div>

						<button type="submit"
							class="btn btn-success btn-flat m-b-30 m-t-30">Recordar
							contraseña</button>


					</form:form>
				</div>
			</div>
		</div>
	</div>

	<script src="resources/assets/js/vendor/jquery-2.1.4.min.js"></script>
	<script src="resources/assets/js/popper.min.js"></script>
	<script src="resources/assets/js/plugins.js"></script>
	<script src="resources/assets/js/main.js"></script>

</body>
</html>