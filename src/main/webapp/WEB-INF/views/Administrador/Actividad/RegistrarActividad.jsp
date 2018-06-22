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
                                    <li><a href="${contextPath}/actividades">Actividades / </li>
                                    <li class="active"><a href="#"> Registrar Actividad</li>
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
                            	<strong class="card-title">Registrar actividad</strong>
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
                        		<form:form id="formActividad" action="guardarActividad" method="post" modelAttribute="actividad" enctype="multipart/form-data">
                            		<!-- Campo para digitar el nombre -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">Nombre</label>
                                		<form:input id="nombre" path="nombre" class="form-control" placeholder="Encuentro de egresados 2019" aria-invalid="false" required = "true"/>
                                	</div> 
                            		<!-- Campo para digitar el lugar -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">Lugar</label>
                                		<form:input id="lugar" path="lugar" class="form-control" placeholder="Hotel tonchala" aria-invalid="false" required = "true"/>
                                	</div> 
									<!-- Campos de las fechas -->
									<div class="row">
										<!-- Fecha inicial -->
										<div class="col-md-6" >
											<div class="form-group"> <!-- Date input -->
									        	<label class="control-label" for="date">Fecha inicial</label>
									        	<form:input type="date" id="fechaInicial" path="fechaInicial" class="form-control" aria-invalid="false" required = "true"/>
									      	</div>
										</div>
										<!--/ Fin de fecha inicial -->
		
										<!-- Fecha final -->
										<div class="col-md-6" >
									      	<div class="form-group"> <!-- Date input -->
									        	<label class="control-label" for="date">Fecha final</label>
									        	<form:input type="date" id="fechaFinal" path="fechaFinal" class="form-control" aria-invalid="false" required = "true"/>
									      	</div>
										</div>
										<!--/ Fin de fecha final -->
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
