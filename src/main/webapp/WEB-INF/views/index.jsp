
<%@ page language="java"
	contentType="text/html; application/octet-stream;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

		<!--header-v6-->
		<ul class="pgwSlider">
			
			<c:forEach var="noticia" items="${noticias}">
				<li>
					<c:choose>
						<c:when test="${not empty noticia.contenido}">
							<c:choose>
								<c:when test="${noticia.contenido.tipoContenido.id == 2}">
									<a href="${noticia.contenido.contenido}">
										<img src="${noticia.im1Base64image}" alt="">
											<span	style="font-family: inherit; font-size: 0.95em; font-weight: normal; color: #111; cursor: auto;">											
												${noticia.descripcion}												
											</span>
										</a>
																											
								</c:when>
								<c:otherwise>
									<a href="${contextPath}/servicios/componente?id=${noticia.id}&componente=noticia">
										<img src="${noticia.im1Base64image}" alt="">
											<span	style="font-family: inherit; font-size: 0.95em; font-weight: normal; color: #111; cursor: auto;">											
													${noticia.descripcion}													
											</span>
										</a>									
								</c:otherwise>		
							</c:choose>		
												    
						</c:when>
					<c:otherwise>
					
						<a>
							<img src="${noticia.im1Base64image}" alt="">
							<span	style="font-family: inherit; font-size: 0.95em; font-weight: normal; color: #111; cursor: auto;">
								${noticia.descripcion}
							</span>
						</a>	
					</c:otherwise>
				</c:choose>
				</li>
			</c:forEach>

		</ul>
		<!--Entra a html_noticias-->
		<!--Antes de acceder a la base de datos-->
		<!--Despues de acceder a la base de datos-->
		<div style="clear: both;"></div>
		<div style="background-color: #e8e8e8;">
			<div class="container content-prin profile">
				<div class="row margin-top-10">
					<div
						class="headline-center-v2 headline-center-v2-dark margin-bottom-10">
						<h1 class="shop-h1" style="font-size: 30px;">
							<b>Novedades</b>
						</h1>
						<span class="bordered-icon"><i class="fa fa-newspaper-o"
							aria-hidden="true"></i></span>
					</div>
					<div class="col-md-12">
						<div class="row equal-height-columns margin-bottom-10">
							<div class="container">
								<ul class="row block-grid-v2">							
									<c:set var="count" value="0" scope="page" />		
									<c:forEach var="novedad" items="${novedades}">										
										<li class="col-md-3 col-sm-6 md-margin-bottom-30" style="padding-left: 14px;">										
											<div class="easy-block-v1">
												<img onclick="openModalImage('modal${count}')" src=" ${novedad.imBase64image}" alt=""
													style="cursor: zoom-in;">
												<div class="easy-block-v1-badge rgba-red"> ${novedad.fechaEnFormato} </div>
											</div>										
											<div
												class="block-grid-v2-info rounded-bottom  bloques_eventos">
												<h5>
													<b>
													
														<c:choose>
															<c:when test="${not empty novedad.contenido}">
																<c:choose>
																	<c:when test="${novedad.contenido.tipoContenido.id == 2}">									
																		<a href="${novedad.contenido.contenido}">
																			${novedad.nombre}													
																		</a>
																	</c:when>
																	<c:otherwise>
																		<a href="${contextPath}/servicios/componente?id=${novedad.id}&componente=novedad">
																			${novedad.nombre}													
																		</a>
																	</c:otherwise>		
																</c:choose>		
																					    
															</c:when>
															<c:otherwise>
																<a>
																	${novedad.nombre}													
																</a>
															</c:otherwise>
														</c:choose>													

													</b>
												</h5>											
											</div>
										</li>
										<c:set var="count" value="${count + 1}" scope="page"/>
									</c:forEach>
								</ul>
								<a href="${contextPath}/servicios/novedades"
									class="btn-u btn-u-sm pull-right tooltips"
									data-toggle="tooltip" data-placement="left"
									data-original-title="Ver m&aacute;s novedades">Ver m&aacute;s
									<i class="fa fa-chevron-circle-right" aria-hidden="true"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- FIN EVENTOS -->
		<div style="background-color: #b43432; padding-bottom: 10px;">
			<div class="container content-prin profile">
				<div class="row margin-bottom-10 margin-top-10">
					<div class="headline-center-v2 margin-bottom-10">
						<h1 style="font-size: 30px; color: #fff;">
							<b>Pr&oacute;ximas actividades</b>
						</h1>
						<span class="bordered-icon"><i class="fa fa-calendar-o"
							aria-hidden="true"></i></span>
					</div>
					
					<c:forEach var="actividad" items="${actividades}">																					
						<div class="col-sm-3">
							
							<c:choose>
								<c:when test="${not empty actividad.contenido}">
									<c:choose>
										<c:when test="${actividad.contenido.tipoContenido.id == 2}">									
											<a href="${actividad.contenido.contenido}">
												<div class="service-block-v1 md-margin-bottom-50" style="background: #fff; border-top: 5px solid #f1c40f;">									
													<i class="icon-custom icon-lg rounded-x icon-color-yellow icon-line fa fa-bookmark" style="background: #fff;"></i>
													<h5 class="title-v3-bg text-uppercase">
														Actividad: ${actividad.nombre}
														<b></b>
													</h5>
													
													<p>Lugar: ${actividad.lugar}</p>
													<p>Fecha: ${actividad.fechaEnFormato}</p>
												</div>
											</a>
										</c:when>
										<c:otherwise>

											<a href="${contextPath}/servicios/componente?id=${actividad.id}&componente=proximaactividad">
												<div class="service-block-v1 md-margin-bottom-50" style="background: #fff; border-top: 5px solid #f1c40f;">									
													<i class="icon-custom icon-lg rounded-x icon-color-yellow icon-line fa fa-bookmark" style="background: #fff;"></i>
													<h5 class="title-v3-bg text-uppercase">
														Actividad: ${actividad.nombre}
														<b></b>
													</h5>
													
													<p>Lugar: ${actividad.lugar}</p>
													<p>Fecha: ${actividad.fechaEnFormato}</p>
												</div>
											</a>
										</c:otherwise>		
									</c:choose>																							    
								</c:when>
							<c:otherwise>
								<a>
									<div class="service-block-v1 md-margin-bottom-50" style="background: #fff; border-top: 5px solid #f1c40f;">									
										<i class="icon-custom icon-lg rounded-x icon-color-yellow icon-line fa fa-bookmark" style="background: #fff;"></i>
											<h5 class="title-v3-bg text-uppercase">
												Actividad: ${actividad.nombre}
												<b></b>
											</h5>
											<p>Lugar: ${actividad.lugar}</p>
											<p>Fecha: ${actividad.fechaInicial}</p>
									</div>
								</a>
							</c:otherwise>
						</c:choose>		

						</div>
					</c:forEach>					
				</div>
				<a 	href="${contextPath}/servicios/actividades" class="btn-u btn-u-sm pull-right tooltips"
					data-toggle="tooltip" data-placement="left"
						data-original-title="Ver m&aacute;s novedades">Ver m&aacute;s
						<i class="fa fa-chevron-circle-right" aria-hidden="true"></i>
				</a>
				
			</div>
		</div>
		<!-- FIN DESTACADOS -->
		<div style="clear: both;"></div>
		<div style="background-color: #d4d4d4;">
			<div class="container content-prin profile">
				<div class="row margin-top-10">
					<div
						class="headline-center-v2 headline-center-v2-dark margin-bottom-10">
						<h1 class="shop-h1" style="font-size: 30px; color: #444;">
							<b>Galer&iacute;as</b>
						</h1>
						<span class="bordered-icon" style="color: #444;"><i
							class="fa fa-newspaper-o" aria-hidden="true"></i></span>
					</div>
					<div class="col-md-12">
						<div class="row equal-height-columns margin-bottom-10">
							<div class="container">
								<ul class="row block-grid-v2">
								
									<c:forEach var="galeria" items="${galerias}">																					
										<li class="col-md-2 col-sm-3 col-xs-6 md-margin-bottom-30" style="padding-left: 14px;">
											<div class="easy-block-v1">
												<a href="${contextPath}/servicios/galeria?id=${galeria.id}"> 
													<img src="${galeria.primeraImagen}" alt="">
												</a>
											</div>
											<div class="block-grid-v2-info rounded-bottom  bloques_eventos">
												<h5>
													<b>
														<a href="${contextPath}/servicios/galeria?id=${galeria.id}">
															${galeria.nombre}
														</a>
													</b>
												</h5>
											</div>
										</li>
									
									</c:forEach>	
								</ul>
								<a href="${contextPath}/servicios/galerias"
									class="btn-u btn-u-sm pull-right tooltips"
									data-toggle="tooltip" data-placement="left"
									data-original-title="Ver m&aacute;s galer&iacute;as">
									Ver m&aacute;s
									<i class="fa fa-chevron-circle-right" aria-hidden="true"></i>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- FIN EVENTOS -->
		<!-- ICONOS REDES SOCIALES -->
		
		<!-- FIN ICONOS REDES SOCIALES -->
		
		<!--  MODALES -->
		<c:set var="count" value="0" scope="page" />
		<c:forEach var="novedad" items="${novedades}">																					
			<div id="modal${count}" class="ufps-image-modal">
				<span class="ufps-image-modal-close">&times;</span> 
				<img class="ufps-image-modal-content" src="${novedad.imBase64image}" alt="">
				<div class="ufps-image-modal-caption">
					${novedad.nombre}
				</div>
			</div>			
			<c:set var="count" value="${count + 1}" scope="page"/>		
		</c:forEach>	

	
		
	</div>
	<!--wrapper-->
	
	
	<%@ include file="General/DownPanel.jsp"%>

	<%@ include file="General/Scripts.jsp"%>
		
</body>
</html>


