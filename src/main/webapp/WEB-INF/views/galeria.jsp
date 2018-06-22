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
                		<h1 class="pull-left" style="font-size:36px;">Galerías de imágenes</h1>
              		</div>
              
              		<h1 class="tituloGaleria">
                    	${galeria.nombre}         
               		</h1>
               		${galeria.descripcion}
              		<div style="clear:both; margin-bottom:15px;"></div>
              		<c:set var="count" value="0" scope="page" />
	            	<c:forEach var="imagen" items="${galeria.imagenes}">
						<div class="img-intro-right30 img-intro-right">
	              			<img onclick="openModalImage('modal${count}')" src="${imagen.imagen}" class="imgInformacion" alt="">
	              			<c:set var="count" value="${count + 1}" scope="page"/>
	            		</div>
					</c:forEach>
              
					<div style="clear:both"></div>
					<p class="readmore-center readmore">
				   		<a href="${contextPath}/servicios/galerias">
				   			Ir a las galerías
				   		</a>
					</p>
					<div style="clear:both"></div>
					<div style="min-height:30px;"></div>
            	</div><!--informacionContent-->
            	
			<%@ include file="General/RightPanel.jsp"%>
			
 			</div><!-- row margin-bottom-30-->
        </div><!--container content profile-->		

		<c:set var="cou" value="0" scope="page" />
		<c:forEach var="imagen" items="${galeria.imagenes}">
			
			<div id="modal${cou}" class="ufps-image-modal">
				<span class="ufps-image-modal-close">×</span>
				<c:choose>
					<c:when test="${cou == 0}">
						<span class="ufps-image-modal-prev" onclick="MyShow('modal${count-1}')">&lt;</span>
						<span class="ufps-image-modal-next" onclick="MyShow('modal${cou+1}')">&gt;</span>
					</c:when>		
					<c:when test="${cou == count-1}">
						<span class="ufps-image-modal-prev" onclick="MyShow('modal${cou-1}')">&lt;</span>
						<span class="ufps-image-modal-next" onclick="MyShow('modal0')">&gt;</span>
					</c:when>									
					<c:otherwise>
						<span class="ufps-image-modal-prev" onclick="MyShow('modal${cou-1}')">&lt;</span>
						<span class="ufps-image-modal-next" onclick="MyShow('modal${cou+1}')">&gt;</span>
					</c:otherwise>
				</c:choose>
	
				<img class="ufps-image-modal-content" src="${imagen.imagen}">
				<div class="ufps-image-modal-caption">
					${imagen.descripcion}
				</div>
			</div>
			<c:set var="cou" value="${cou + 1}" scope="page"/>
		</c:forEach>		
	</div>
	<!--wrapper-->
	
	<%@ include file="General/DownPanel.jsp"%>

	<%@ include file="General/Scripts.jsp"%>

</body>
</html>


