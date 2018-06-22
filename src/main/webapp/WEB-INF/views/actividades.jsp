
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="es">
<!--<![endif]-->
<head>
	<%@ include file="General/Css.jsp"%>
</head>
<body class="header-fixed boxed-layout" style="position: relative; min-height: 100%; top: 0px;">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<!--Contenido-->
	<div class="wrapper">
		
		<%@ include file="General/UpPanel.jsp"%>
		
        <div class="container content profile">
			<div class="row margin-bottom-30">

             	<div style="clear:both;">
             	</div>
            	<div id="informacionContent" class="col-md-8 mb-margin-bottom-30 shadow-wrapper">
              		<div class="col-md-12 col-sm-12 col-xs-12" style="margin-bottom:20px; border-bottom: 3px solid #aa1916; padding: 0;">
                		<h1 class="pull-left" style="font-size:36px;">
                			Actividades
                		</h1>
              		</div>
              
              		<!-- Contenido creado dinamicamente -->
					<c:forEach var="actividad" items="${actividadCom}">																					

						<div class="items-row cols-153 row-0">
							<div class="item-information column-1">

				              	
				              	<div class="float_content">
						        	<h1 class="tituloinformacion">
						            	<a class="tituloGaleria" href="${contextPath}/servicios/componente?id=${actividad.id}&componente=proximaactividad"> ${actividad.nombre} </a>                  
						            </h1>
									<p>Lugar: ${actividad.lugar}</p>
									<p>Fecha: ${actividad.fechaEnFormato}</p>          
							 	</div>
				              	
				              	<div style="clear:both"></div>
				              	
				              	<p class="readmore">
				                	<a href="${contextPath}/servicios/componente?id=${actividad.id}&componente=proximaactividad"> Leer m&aacute;s...</a>
				              	</p>
								<div style="clear:both"></div>
				              	<span class="row-separator"></span>              
							</div>
						</div>						
						
					</c:forEach>	
					
					<p style="text-align: left;">&nbsp;</p>
					<p style="text-align: left;">&nbsp;</p>
					<p style="text-align: left;">&nbsp;</p>            
					<div style="clear:both; min-height:30px;"></div>
        		</div><!--informacionContent-->
        		<%@ include file="General/RightPanel.jsp"%>		
			</div><!--container content profile-->		
		</div><!-- row margin-bottom-30-->
		
	</div>	
	<!--wrapper-->
	
	<%@ include file="General/DownPanel.jsp"%>
	
	<%@ include file="General/Scripts.jsp"%>
	
</body>
</html>


