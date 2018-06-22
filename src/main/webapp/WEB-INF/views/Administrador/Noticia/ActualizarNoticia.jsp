
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
                                    <li><a href="${contextPath}/noticias">Noticias / </li>
                                    <li class="active"><a href="#"> Actualizar Noticia</li>
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
                            	<strong class="card-title">Actualizar noticia</strong>
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
                        		<form:form id="formNoticia" action="editarNoticia" method="post" modelAttribute="noticia" enctype="multipart/form-data">
                            		
                            		 <form:hidden id="id" path="id" class="form-control" aria-invalid="false" required = "true" value="${noticia.id}"/>
                            			<form:hidden id="im1Base64image" path="im1Base64image" class="form-control"  aria-invalid="false" value="Lleno"/>
                            			
                            		
                            		<!-- Campo para digitar el nombre -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">Nombre</label>
                                		<form:input id="nombre" path="nombre" class="form-control"  aria-invalid="false" required = "true" value="${noticia.nombre}"/>
                                	</div> 
                                	<!-- Campo para digitar la descripción -->
                                	<div class="form-group">
                                        <label for="textarea-input" class=" form-control-label">Descripción</label>
                                		<form:textarea id="descripcion" maxlength="160" name="descripcion" class="form-control" path="descripcion" rows="2" cols="130" required = "true" value="${noticia.descripcion}" /> 
                                	</div>   

                            		<!-- Campo para digitar la fecha -->
                                	<div class="form-group">
                                    	<label for="text-input" class=" form-control-label">Fecha del suceso</label>
                                		<form:input type="date" id="fecha" path="fecha" class="form-control" placeholder="Servicios" aria-invalid="false" required = "true" value="${noticia.fecha}"/>
                                	</div>
                                	
 									<!-- Campo para digitar la imagen 1 -->
                                	<div id ="divim1" class="form-group btn btn-primary btn-sm">
                                    	<label for="text-input" class=" form-control-label">Imagen (Pref. 1950x550) </label>
                                		<form:input type="file" path="Imagen1" id="Imagen1" name="Imagen1" onchange="revisarArchivos('1')"/>
                                	</div>
                                	</br>
                                	<div id = "divimagen1" class="form-group">
                                		<img id = "img1" height="200" alt="Imagen" src="${noticia.im1Base64image}">
                                	</div>
                                	</br>
                                	                                	                              		                                                               	                                	                              	
                                	
                                	<!-- Boton para actualizar los datos -->
                                	<button type="submit" class="btn btn-success">Actualizar</button>                                 
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
	
	<!-- <script src="resources/assets/js/archivos.js"></script>  -->
	<script type="text/javascript">
	function revisarArchivos(id){
		var el = document.getElementById("Imagen"+id).files;
		if(el!=null && el.length==0){
			document.getElementById("divim"+id).setAttribute('class', 'btn btn-danger btn-sm');
			previewFile(id);
		}else{
			document.getElementById("divim"+id).setAttribute('class', 'btn btn-primary btn-sm');
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
