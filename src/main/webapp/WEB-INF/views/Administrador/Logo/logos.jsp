<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
								<li class="active" href="#">Logos</li>
								</li>
							</ol>
						</div>
					</div>
				</div>

				<!-- Area en donde se encuentra la foto del usuario y la barra de opciones -->
				<div class="col-sm-5">
					<div class="user-area dropdown float-right">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false"> <img
							class="user-avatar rounded-circle"
							src="resources/images/admin.jpg" alt="User Avatar">
						</a>
						<div class="user-menu dropdown-menu">
							<a class="nav-link" href="#"><i class="fa fa- user"></i>Mi
								perfil</a> <a class="nav-link" href="#"><i class="fa fa -cog"></i>Configuración
								de la cuenta</a> <a class="nav-link" href="${contextPath}/logout"><i
								class="fa fa-power -off"></i>Salir</a>
						</div>
					</div>
				</div>
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
								<strong class="card-title">Logos</strong>
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
							    
                        		<!-- Si hubo un error en el registro muestra el mensaje-->						
								<c:if test="${not empty wrong}">		            
		                        	<div class="sufee-alert alert with-close alert-danger alert-dismissible fade show">				                   	
				                    		<c:out value='${wrong}' />
				                    	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
											<span aria-hidden="true">&times;</span>
				                    	</button>
				                   </div>
							    </c:if>							    

								<form:form id="formlogo" action="guardarLogo" method="post" modelAttribute="logo" enctype="multipart/form-data">
                                	
                            		 <form:hidden id="id" path="id" class="form-control" aria-invalid="false" required = "true" value="${logoHorizontal.id}"/>
                            		 <form:hidden id="tipo" path="tipo" class="form-control" aria-invalid="false" required = "true" value="LogoHorizontal"/>
                                	<div class="form-group">
	                                    <strong class="card-title">Logo horizontal</strong>                                    
	                                	</br>
	                    
	                                	<div class="form-group">
	                                    
	                                    	<figure>
	                                        	<img id = "img1" src="${logoHorizontal.imBase64image}" alt="Logo horizontal" width="800" height="228">                                        
	                                    	</figure>                                    
	                                    	<form:input type="file" path="contenido" id="Imagen1" name="Imagen1" onchange="revisarArchivos('1')" required = "true"/>
	                            		</div>
	    
	                                	</div>
                                	<button type="submit" class="btn btn-success">Actualizar logo horizontal</button>                                 
                            	</form:form>        
		                        </br>
		                        </br>
		                        </br>		
								<form:form id="formlogo" action="guardarLogo" method="post" modelAttribute="logo" enctype="multipart/form-data">
                                	
                            		 <form:hidden id="id" path="id" class="form-control" aria-invalid="false" required = "true" value="${logoVertical.id}"/>
                            		 <form:hidden id="tipo" path="tipo" class="form-control" aria-invalid="false" required = "true" value="LogoVertical"/>
                                	<div class="form-group">
	                                    <strong class="card-title">Logo vertical</strong>                                    
	                                	</br>
	                    
	                                	<div class="form-group">
	                                    
	                                    	<figure>
	                                        	<img id = "img2" src="${logoVertical.imBase64image}" alt="Logo Vertical" width="500" height="500">                                        
	                                    	</figure>                                    
	                                    	<form:input type="file" path="contenido" id="Imagen2" name="Imagen2" onchange="revisarArchivos('2')" required = "true"/>
	                            		</div>
	    
	                                	</div>
                                	<button type="submit" class="btn btn-success">Actualizar logo Vertical</button>                                 
                            	</form:form>  		                        
		                        						
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
		
		function revisarArchivos(id){
			var el = document.getElementById("Imagen"+id).files;
			if(el!=null && el.length==0){
				previewFile(id);
			}else{
				previewFile(id);
			}
		}
		
		/*
		* Metodo que permite pintar una imagen recien 
		*/
		function previewFile(id) {
			  var preview = document.getElementById('img'+id);
			  var file    = document.getElementById('Imagen'+id).files[0];
			  var reader  = new FileReader();

			  reader.onloadend = function () {
			    preview.src = reader.result;
			    document.getElementById('im'+id+'Base64image').value = "Lleno";
			  }

			  if (file) {
			    reader.readAsDataURL(file);
			  } else {
				  			  
			    preview.src = "";
			    document.getElementById('im'+id+'Base64image').value = "";
			  }
			}	
	</script>

</body>
</html>
