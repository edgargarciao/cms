<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>

 <style>
            div.gallery {
                margin: 5px;
                border: 1px solid #ccc;
                float: left;
                width: 180px;
            }
            
            div.gallery:hover {
                border: 1px solid #777;
            }
            
            div.gallery img {
                width: 100%;
                height: auto;
            }
            
            div.desc {
                padding: 15px;
                text-align: center;
            }
            </style>


    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Admin-Ufps</title>
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
    <link rel="stylesheet" href="resources/assets/css/lib/datatable/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="resources/assets/css/lib/datepicker/datepicker3.css">
    <link rel="stylesheet" href="resources/assets/css/lib/datepicker/datepicker.css">
    <!-- <link rel="stylesheet" href="assets/css/bootstrap-select.less"> -->
    <link rel="stylesheet" href="resources/assets/scss/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

    <!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->


   
</head>
<body>
<!-- Left Panel -->

		<%@ include file = "../General/LeftPanel.jsp" %>
    <!-- Left Panel -->

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
	                                    <li><a href="${contextPath}/galerias">Galerias / </li>
	                                    <li class="active"><a href="#"> &nbsp;Actualizar galeria</li>
	                            	</ol>
                                </div>
                            </div>    

                </div>
                
		        <%@ include file="../General/Configuracion.jsp"%>
        
            </div>

        </header><!-- /header -->
        <!-- Header-->

        <div class="content mt-3">
            <div class="animated fadeIn">
                <div class="row">
	                <div class="col-md-12">
	                    <div class="card">
	                        <div class="card-header">
	                            <strong class="card-title">Actualizar galeria</strong>
	                        </div>
	                        <div class="card-body">

								<!-- Si hubo un error en el registro muestra el mensaje-->
								<div id="exito">
                                </div>
								<div id="error">
                                </div>								
							    
	                            <form:form id="formGaleria" action="guardarGaleria" method="post" modelAttribute="galeria" enctype="multipart/form-data">
	                            	<form:input type="hidden" id="id" path="id" class="form-control" value = "${galeria.id}" />
	                            	<input type="hidden" id="ruta" class="form-control" value = "${contextPath}" />
	                            
	                                <div class="form-group">
	                                    <label for="text-input" class=" form-control-label">Nombre</label>	                                    
	                                	<form:input id="nombre" path="nombre" name="nombre" type="text" class="form-control" aria-invalid="false" required = "true" value="${galeria.nombre}"/>
	                                </div> 
	                                <div class="form-group">
	                                 	<label for="textarea-input" class=" form-control-label">Descripción corta</label>
	                                	<form:textarea id="descripcion" maxlength="180" name="descripcion" class="form-control" path="descripcion" rows="2" cols="130" required = "true" value="${galeria.descripcion}" />
	                                </div>   
	
									<div class="form-group"> <!-- Date input -->
								        <label class="control-label" for="date">Fecha</label>
								        <form:input type="date" id="fecha" path="fecha" class="form-control" aria-invalid="false" required = "true" value="${galeria.fecha}"/>
								    </div>
		                                      
			                        </br>
			                        <div id="galerias" class="form-group"> <!-- Date input -->
     
			                        </div>
										
									<div class="form-group"> <!-- Date input -->
										<label class="control-label" for="date">&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;&emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;  </label>									        
									</div>
										
	                            	<button type="button" onclick="actualizarGaleria()" class="btn btn-success">Actualizar</button> 
	                            </form:form>                                         
	                                   
	                        </div>
	                       
	                    </div>
	                </div>
                

                </div>
            </div><!-- .animated -->
        </div><!-- .content -->


    </div><!-- /#right-panel -->


    <!-- Right Panel -->

	
	<%@ include file="../General/scripts.jsp"%>
   
	<script src="resources/assets/js/server/actualizarGaleria.js"></script>


</body>
</html>
