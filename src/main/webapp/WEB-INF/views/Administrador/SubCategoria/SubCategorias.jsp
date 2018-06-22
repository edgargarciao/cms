<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->
<head>
<%@ include file="../General/css.jsp"%>
</head>
<body>

	<!-- Left Panel -->

	<%@ include file="../General/LeftPanel.jsp"%>

	<!--/ Left Panel -->

	<!-- Right Panel -->

	<div id="right-panel" class="right-panel">

		<!-- Header-->
		<header id="header" class="header">
			<!-- Header menu -->
			<div class="header-menu">
				<div class="col-sm-7">
					<a id="menuToggle" class="menutoggle pull-left"> <i
						class="fa fa fa-tasks"> </i>
					</a>
					<div class="page-header float-left">
						<div class="page-title">
							<ol class="breadcrumb text-right">
								<li class="active">
								<li><a href="${contextPath}/indexAdmin">Panel de
										control</a></li>
								<li class="active" href="#">Subcategorias</li>
								</li>
							</ol>
						</div>
					</div>
				</div>

				<!-- Area en donde se encuentra la foto del usuario y la barra de opciones -->
				<%@ include file="../General/Configuracion.jsp"%>
			</div>
			<!-- /Header menu -->

		</header>
		<!-- /header -->

		<!-- Div content 3 -->
		<div class="content mt-3">
			<!-- Div animated -->
			<div class="animated fadeIn">
				<!-- Row -->
				<div class="row">
					<!-- Col 12 -->
					<div class="col-md-12">
						<!-- Card -->
						<div class="card">
							<div class="card-header">
								<strong class="card-title">Subcategorias</strong>
							</div>
							<div class="card-body">
								<!-- /Card -->

		                	    <!-- Si hubo un registro exitoso muestra el mensaje-->
							    <c:if test="${not empty result}">
							    	<div class="sufee-alert alert with-close alert-success alert-dismissible fade show">
                                    	<c:out value='${result}' />
                                       	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        	<span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
							    </c:if>

								<!-- Boton que indica la accion para registrar una subcategoria -->
								<a href="${contextPath}/registrarSubCategoria"
									class="btn btn-success">Registrar subcategoria</a> <br> <br>
								<!-- Tabla con las subcategorias -->
								<table id="bootstrap-data-table"
									class="table table-striped table-bordered">
									<thead>
										<tr>
											<th scope="col" style="display:none">idCategoria</th>
											<th scope="col" style="width: 7%">Categoria</th>
											<th scope="col" style="width: 1%">Órden</th>
											<th scope="col" style="display:none">Id</th>
											<th scope="col" style="width: 25%">Nombre subcategoria</th>
											<th scope="col" style="width: 35%">Descripción</th>
											<th scope="col" style="width: 34%">Acción</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="subcategoria" items="${subcategorias}">
											<tr>
												<td style="display:none">${subcategoria.categoria.id}</td>
												<td>${subcategoria.categoria.nombre}</td>
												<td scope="row">${subcategoria.orden}</td>
												<td style="display:none">${subcategoria.id}</td>
												<td>${subcategoria.nombre}</td>
												<td>${subcategoria.descripcion}</td>											
												<td><a
													href="${contextPath}/bajarOrdenSubCategoria?idCat=${subcategoria.categoria.id}&id=${subcategoria.id}&orden=${subcategoria.orden}">
														<button class="btn btn-outline-info">
															<i class="fa fa-sort-desc" aria-hidden="true"></i>
														</button>
												</a> <a
													href="${contextPath}/subirOrdenSubCategoria?idCat=${subcategoria.categoria.id}&id=${subcategoria.id}&orden=${subcategoria.orden}">
														<button class="btn btn-outline-info">
															<i class="fa fa-sort-asc" aria-hidden="true"></i>
														</button>
												</a> <a
													href="${contextPath}/actualizarSubCategoria?id=${subcategoria.id}">
														<button class="btn btn-outline-primary">
															<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
														</button>
												</a> <a
													href="${contextPath}/eliminarSubCategoria?id=${subcategoria.id}">
														<button class="btn btn-outline-danger">
															<i class="fa fa-trash" aria-hidden="true"></i>
														</button>
												</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<!-- /Tabla -->
							</div>
							<!-- /card-body -->
						</div>
						<!-- /card -->
					</div>
					<!-- /Col 12 -->
				</div>
				<!-- /Row -->
			</div>
			<!-- .animated -->
		</div>
		<!-- .content -->
	</div>
	<!-- /#right-panel -->

	<!-- Right Panel -->

	<!-- Carga de los archivos Javascript -->
	<%@ include file="../General/scripts.jsp"%>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#bootstrap-data-table-export').DataTable();
		});
	</script>

</body>
</html>
