<div class="col-md-4">
	<div class="gdl-custom-sidebar">
    	<h3 class="gdl-custom-sidebar-title-m">Secciones</h3>
    </div>
    <div class="gdl-custom-sidebar">
    	<a>
    		<button class="ufps-btn-accordionlike">
    			Noticias
    		</button>
    	</a>
        <a href="${contextPath}/servicios/novedades">
        	<button class="ufps-btn-accordionlike">
        		Novedades
        	</button>
        </a>
        <a>
        	<button class="ufps-btn-accordionlike">
        		Eventos
        	</button>
        </a>

    </div>
    
    <div id="proximasContent" class="gdl-custom-sidebar gdl-custom-sidebar-nofirst">
    	<h3 class="gdl-custom-sidebar-title-m">
    		Actividades de esta semana
    		<div class="ufps-tooltip" style="float:right;">
	    		<span style="float:right;">
	    			<a>
	    				<img src="resources/rsc/img/calendar.png">
	    			</a>
	    		</span>
	    		<span class="ufps-tooltip-content-left" style="font-size:14px; font-weight:normal; margin-right:5px;">
	    			Ir al calendario
	    		</span>
    		</div>
    	</h3>
    	
    	<div id="browseweek" class="browseweek" style="background:#424242; min-height:25px; width:100%; margin:auto; margin-bottom:0px;">
    		<div class="ufps-tooltip">
    			<div style="margin-left:2px; margin-right:2px; float:left; top:0px; left:0px; width:25px;">
    				<img src="resources/rsc/img/rewind.png" style="cursor:pointer;" onclick="WeekDirection(0,-1,'right');">
    			</div>
    			<span class="ufps-tooltip-content-right">
    				Semana anterior
    			</span>
    		</div>
	    	<div class="ufps-tooltip" style="float:right;">
	    	
		    	<div style="margin-left:2px; margin-right:2px; float:right; top:0px; right:0px; width:25px;">
		    		<img src="resources/rsc/img/forward.png" style="cursor:pointer;" onclick="WeekDirection(0,+1,'right');">
		    	</div>
		    	
		    	<span class="ufps-tooltip-content-left">
		    		Semana siguiente
		    	</span>
		    </div>
    	
    	</div>
    
    	<span style="text-align:center;">
    		<h3 class="simple" style="margin-top:20px; color:#dd1617;">
    			No hay actividades para mostrar
    		</h3>
    	</span>

        <div class="clear" style="height:10px;">
        </div>

	</div><!--proximasContent-->
	
	<div class="gdl-custom-sidebar gdl-custom-sidebar-nofirst">
  		<h3 class="gdl-custom-sidebar-title-m">Galer&iacute;as</h3>
		
		<c:forEach var="galeria" items="${galerias}">																					
			<div class="col-md-6 col-sm-6 col-xs-6 col-md-margin-bottom-30" style="padding-left: 14px; margin-bottom: 14px;">
				<div class="easy-block-v1">
			    	<a href="${contextPath}/servicios/galeria?id=${galeria.id}">
			      		<img src="${galeria.primeraImagen}" alt="">
			      	</a>
			      	<div class="easy-block-v1-badge rgba-black" style="z-index:0; width:100%; top: 0px; color:#fff; font-size:0.9em;">
			        	${galeria.nombre}      
			        </div>
			    </div>
			</div>
		</c:forEach>		
	</div>


	<div style="clear:both;"></div>


	<div style="clear:both; min-height:26px;">
	</div>
</div><!--col-md-4-->