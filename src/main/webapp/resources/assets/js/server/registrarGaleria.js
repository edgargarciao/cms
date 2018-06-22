		var imagenes = [];
		crearCuadro();
		
		function crearCuadro(){
			var galerias = document.getElementById("galerias");
			
			var id = Math.floor(Math.random() * 1000) + 1;
			
			// Se crea el div general
			var div = document.createElement("DIV");
			div.setAttribute("id","gal"+id);
			div.setAttribute("class","gallery");

			/*****************************************
			*		Se crea la primera seccion
			******************************************/
			
			// Se crea el item "a"
			var a = document.createElement("a");
			a.setAttribute("target","_blank");
			
			// Se crea un item "img"
			var img = document.createElement("img");
			img.setAttribute("id","img"+id);
			img.setAttribute("src","http://www.clker.com/cliparts/M/I/9/z/q/H/male-upload-md.png");
			img.setAttribute("width",600);
			img.setAttribute("height",400);
			
			// Se añade la imagen al label a
			a.appendChild(img);
			
			// Se añade al div general
			div.appendChild(a);
			
			/*****************************************
			*		Se crea la segunda seccion
			******************************************/
			
			// Se crea el item div
			var div1 = document.createElement("div");
			div1.setAttribute("class","desc");
			
			// Se crea el input
			var input = document.createElement("input");
			input.setAttribute("type","file");
			input.setAttribute("id","Imagen"+id);
			input.setAttribute("name","Imagen"+id);
			input.setAttribute("accept","image/*");
			input.setAttribute("onchange","revisarArchivos('"+id+"')");
			input.setAttribute("required","true");
			
			// Se añade el input al div
			div1.appendChild(input);
			
			// Se añade el div al div general
			div.appendChild(div1);
			
			
			/*****************************************
			*		Se crea la tercera seccion
			******************************************/
			// Se crea el item div
			var div2 = document.createElement("div");
			div2.setAttribute("class","desc");
			
			// Se crea el input
			var textarea = document.createElement("textarea");
			textarea.setAttribute("id","textarea-input"+id);
			textarea.setAttribute("name","textarea-input"+id);
			textarea.setAttribute("rows",2);
			textarea.setAttribute("placeholder","Descripción de la imagen");
			textarea.setAttribute("class","form-control");
			
			// Se añade el input al div
			div2.appendChild(textarea);
			
			// Se añade el div al div general
			div.appendChild(div2);
			
			/*****************************************
			*		Se crea la cuarta seccion
			******************************************/
			// Se crea el item div
			var div3 = document.createElement("div");
			div3.setAttribute("class","desc");
			
			// Se crea el input
			var button = document.createElement("button");
			button.setAttribute("id",id);
			button.setAttribute("name","imag"+id);
			button.setAttribute("type","button");
			button.setAttribute("onclick","cargarImagen(this.id)");
			button.setAttribute("class","btn btn-success");
			
			// Se crea el texto
			var texto = document.createTextNode("Guardar foto");       
			
			// Se añade el text al boton
			button.appendChild(texto);
			
			// Se añade el input al div
			div3.appendChild(button);
			
			// Se añade el div al div general
			div.appendChild(div3);
			
			/*****************************************
			*		Se añade el div general al area
			******************************************/			
			galerias.appendChild(div);
		}
		
		function cargarImagen(id){
			if(guardarImagen(id)){
				cambiarDiseno(id);
				crearCuadro();
			}
		}
		
		function guardarImagen(id){
			var imagen = document.getElementById('img'+id).src;
			var descripcion = document.getElementById('textarea-input'+id).value;
			var im = {
					id:id,
					imagen:imagen,
					descripcion:descripcion
			}
			imagenes.push(im);		
			return true;
		}
		
		function cambiarDiseno(id){
			// Se obtienen los datos
			var src = document.getElementById('img'+id).src;
			var descripcion = document.getElementById('textarea-input'+id).value;
			
			// Se limpia la seccion
			document.getElementById('gal'+id).innerHTML = "";
			
			// Se obtiene el div general
			var div = document.getElementById('gal'+id);

			/*****************************************
			*		Se crea la primera seccion
			******************************************/
			
			// Se crea el item "a"
			var a = document.createElement("a");
			a.setAttribute("target","_blank");
			
			// Se crea un item "img"
			var img = document.createElement("img");
			img.setAttribute("id","img"+id);
			img.setAttribute("src",src);
			img.setAttribute("width",250);
			img.setAttribute("height",250);
			
			// Se añade la imagen al label a
			a.appendChild(img);
			
			// Se añade al div general
			div.appendChild(a);
			
			/*****************************************
			*		Se crea la segunda seccion
			******************************************/
			
			// Se crea el item div
			var div1 = document.createElement("div");
			div1.setAttribute("class","desc");
			
			// Se crea el texto
			var texto = document.createTextNode(descripcion);   
			
			// Se añade el input al div
			div1.appendChild(texto);
			
			// Se añade el div al div general
			div.appendChild(div1);
			
			
			/*****************************************
			*		Se crea la tercera seccion
			******************************************/
			// Se crea el item div
			var div3 = document.createElement("div");
			div3.setAttribute("class","desc");
			
			// Se crea el input
			var button = document.createElement("button");
			button.setAttribute("id",id);
			button.setAttribute("name","imag"+id);
			button.setAttribute("type","button");
			button.setAttribute("onclick","eliminarImagen(this.id)");
			button.setAttribute("class","btn btn-danger");
			
			// Se crea el texto
			var texto = document.createTextNode("Eliminar foto");       
			
			// Se añade el text al boton
			button.appendChild(texto);
			
			// Se añade el input al div
			div3.appendChild(button);
			
			// Se añade el div al div general
			div.appendChild(div3);
				
			
		}
		         
		function eliminarImagen(id){
			if(borrarImagen(id)){
				 var elem = document.getElementById('gal'+id);
				 elem.parentNode.removeChild(elem);
				 
			}
		}
		
		function borrarImagen(id){
			for(var i in imagenes){
			    if(imagenes[i].id == id){
			        imagenes.splice(i,1);
			        break;
			    }
			}
			return true;
		}		
		
		/*
		* Metodo que permite pintar una imagen recien 
		*/
		function revisarArchivos(id) {
			var preview = document.getElementById('img'+id);
			var file    = document.getElementById('Imagen'+id).files[0];
			var reader  = new FileReader();
			  
			reader.onloadend = function () {
				var image = new Image();
				image.src = reader.result;
				
				image.onload = function() {  

					preview.src = image.src;
				};
				//
			}

			if (file) {
				reader.readAsDataURL(file);
			} else {
			    preview.src = "http://www.clker.com/cliparts/M/I/9/z/q/H/male-upload-md.png";
			}
		}		
		
		function guardarGaleria(){
			
			// Aqui se obtiene el nombre o titlo
			var nombre = document.getElementById("nombre").value;
			
			// Aqui se obtiene la descripcion
			var descripcion = document.getElementById("descripcion").value;
			
			// Aqui se obtiene la fecha
			var fecha = document.getElementById("fecha").value;			
				
			
			
			var ruta = document.getElementById("ruta").value;
			var formData = {
					nombre: 			nombre,
					descripcion: 		descripcion,
					fecha: 				fecha,
					id: 				0,
					imagenes:			imagenes
			};

			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : ruta + "/servicios/guardarGaleria",
				data: JSON.stringify(formData),
				success : function(result) {
						
					if(result.trim() == 'REGISTRO EXITOSO'){
							
						$('#formGaleria').trigger("reset");
						
						pintarRegistroExitoso();
						document.getElementById('galerias').innerHTML = "";
						crearCuadro();
						
					}else{
						pintarRegistroNoExitoso(result.trim());
					}
					
				},
				error : function(e) {
					pintarRegistroNoExitoso("Error en el sistema. Contacte al administrador.");
					console.log("ERROR: ", e);
				}
				});	
		}
		
		function pintarRegistroExitoso(){
			var exito = document.getElementById("exito");
			
			var div = document.createElement("DIV");
			div.setAttribute("class","sufee-alert alert with-close alert-success alert-dismissible fade show");
			var texto = document.createTextNode("Registro exitoso");       
			div.appendChild(texto);
			
			var boton = document.createElement("BUTTON");
			boton.setAttribute("type","button");
			boton.setAttribute("class","close");
			boton.setAttribute("data-dismiss","alert");
			boton.setAttribute("aria-label","Close");
			
			var span = document.createElement("span");
			span.setAttribute("aria-hidden","true");
			var textos = document.createTextNode("X");       
			span.appendChild(textos);
			
			boton.appendChild(span);
			div.appendChild(boton);
			exito.appendChild(div);
		}
		
		function pintarRegistroNoExitoso(mensaje){
			var error = document.getElementById("error");
			
			var div = document.createElement("DIV");
			div.setAttribute("class","sufee-alert alert with-close alert-danger alert-dismissible fade show");
			var texto = document.createTextNode(mensaje);       
			div.appendChild(texto);
			
			var boton = document.createElement("BUTTON");
			boton.setAttribute("type","button");
			boton.setAttribute("class","close");
			boton.setAttribute("data-dismiss","alert");
			boton.setAttribute("aria-label","Close");
			
			var span = document.createElement("span");
			span.setAttribute("aria-hidden","true");
			var textos = document.createTextNode("X");       
			span.appendChild(textos);
			
			boton.appendChild(span);
			div.appendChild(boton);
			error.appendChild(div);
		}
		
