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
                                    <li><a href="${contextPath}/subcategorias">Subcategorias / </li>
                                    <li class="active"><a href="#"> Eliminar subcategoria</li>
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
                            	<strong class="card-title">Eliminar subcategoria</strong>
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
                        		<form:form id="formCategoria" action="borrarSubCategoria" method="post" modelAttribute="subcategoria">
                        		                 			
                        		    <form:hidden id="id" path="id" class="form-control" aria-invalid="false" required = "true" value="${subcategoria.id}"/>
                            		<form:hidden id="orden" path="orden" class="form-control" aria-invalid="false" required = "true" value="${subcategoria.orden}"/>
                            		<form:hidden id="nombre" path="nombre" class="form-control" aria-invalid="false" required = "true" value="${subcategoria.nombre}"/>
                            		<form:hidden id="descripcion" path="descripcion" class="form-control" aria-invalid="false" required = "true" value="${subcategoria.descripcion}"/>
                            		<form:hidden id="categoria.id" path="categoria.id" class="form-control" aria-invalid="false" required = "true" value="${categoria.id}"/>
                            		
                            		<!-- Campo para digitar el nombre -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">¿Estás seguro de eliminar la subcategoría "${subcategoria.nombre}"?</label>                                		
                                	</div> 
                                	
                                	<!-- Boton para Eliminar los datos -->
                                	<button type="submit" class="btn btn-danger">Eliminar</button>                                 
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
