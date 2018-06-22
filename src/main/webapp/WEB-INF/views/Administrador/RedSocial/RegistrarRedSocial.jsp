<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>
	<%@ include file = "../General/css.jsp" %>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
	<!-- Left Panel -->

		<%@ include file = "../General/LeftPanel.jsp" %>

	<!-- /#left-panel -->

    <!-- Right Panel -->

    <div id="right-panel" class="right-panel">

        <!-- Header-->
        <header id="header" class="header">

            <div class="header-menu">
            	<div class="col-sm-7">
                        <a id="menuToggle" class="menutoggle pull-left"><i class="fa fa fa-tasks"></i></a>
                        <div class="page-header float-left">
                        	<div class="page-title">
                                <ol class="breadcrumb text-right">
                                	<li><a href="${contextPath}/indexAdmin">Panel de control</a></li>
                                    <li><a href="${contextPath}/redesSociales">Redes sociales / </li>
                                    <li class="active"><a href="#"> Registrar Redes socales</li>
                            	</ol>
                            </div>
						</div>    
                </div>
               	<!-- Area en donde se encuentra la foto del usuario y la barra de opciones -->
				<%@ include file="../General/Configuracion.jsp"%>
            </div>

        </header><!-- /header -->
        <!-- Header-->
		
		<!-- Contenedor del formulario -->
        <div class="content mt-3">
            <div class="animated fadeIn">
                <div class="row">
                	<div class="col-md-12">                	
 	
                    	<div class="card">
                    		<!-- Titulo de la ventana -->
                        	<div class="card-header">
                            	<strong class="card-title">Registrar Redes sociales</strong>
                        	</div>
                        	<div class="card-body">
                        		<!-- Si hubo un error en el registro muestra el mensaje-->						
								<c:if test="${not empty wrong}">		            
		                        	<div class="sufee-alert alert with-close alert-danger alert-dismissible fade show">				                   	
				                    		<c:out value='${wrong}' />
				                    	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
											<span aria-hidden="true">&times;</span>
				                    	</button>
				                   </div>
							    </c:if>
                        	
                        		<!-- Formulario -->
                        		<form:form id="formRedSocial" action="guardarRedSocial" method="post" modelAttribute="redSocial" enctype="multipart/form-data">
                            		<!-- Campo para digitar el nombre -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">Nombre</label>
                                		<form:input id="nombre" path="nombre" class="form-control" placeholder="Nombre de la red social" aria-invalid="false" required = "true"/>
                                	</div> 
                                	<!-- Campo para digitar la url -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">URL</label>
                                		<form:input id="url" path="url" class="form-control" placeholder="URL de la red social" aria-invalid="false" required = "true"/>
                                	</div> 

                                	<div class="form-group">
                                	
                                	
	                                <label for="text-input" class=" form-control-label">Icono</label>
	                                
		                                <select id="logo" name='logo'  style='height: 60px; width: 150px; font-family:Arial, FontAwesome;' class="form-control">
										
											<option value='fa-facebook' >&#xf09a; &nbsp; Facebook</option>
											<option value='fa-twitter'>&#xf099; &nbsp; Twitter</option>
											<option value='fa-youtube'>&#xf167; &nbsp; Youtube</option>
											<option value='fa-instagram'>&#xf16d; &nbsp; Instagram</option>
											<option value='fa-linkedin'>&#xf0e1; &nbsp; LinkedIn</option>
											<option value='fa-play-circle'>&#xf144; &nbsp; Radio</option>
											<option value='fa-rss'>&#xf09e; &nbsp; RSS</option>
																			
										</select>
	                            	</div>
	                            		
                                	<!-- Boton para registrar los datos -->
                                	<button type="submit" class="btn btn-success">Registrar</button>                                 
                            	 </form:form>                          
                        	</div>
                    	</div>
                	</div>
                </div>
            </div><!-- .animated -->
        </div><!-- .content -->


    </div><!-- /#right-panel -->

    <!-- Right Panel -->


	<!-- Carga de los archivos Javascript -->
	<%@ include file = "../General/scripts.jsp" %>

</body>
</html>
