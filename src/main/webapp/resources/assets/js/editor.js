  var colorPalette = ['000000', 'FF9966', '6699FF', '99FF66', 'CC0000', '00CC00', '0000CC', '333333', '0066FF', 'FFFFFF'];
  var forePalette = $('.fore-palette');
  var backPalette = $('.back-palette');

  for (var i = 0; i < colorPalette.length; i++) {
    forePalette.append('<a href="#" data-command="forecolor" data-value="' + '#' + colorPalette[i] + '" style="background-color:' + '#' + colorPalette[i] + ';" class="palette-item"></a>');
    backPalette.append('<a href="#" data-command="backcolor" data-value="' + '#' + colorPalette[i] + '" style="background-color:' + '#' + colorPalette[i] + ';" class="palette-item"></a>');
  }

  
  /**
   * Evento que se acciona cuando dan clic en alguna opcion de la paleta.
   * @param e Evento.
   * @returns
   */
  $('.toolbar a').click(function(e) {
    var command = $(this).data('command');
    if (command == 'h1' || command == 'h2' || command == 'p') {
      document.execCommand('formatBlock', false, command);
    }
    else if (command == 'forecolor' || command == 'backcolor') {
      document.execCommand($(this).data('command'), false, $(this).data('value'));
    }
    else if (command == 'createlink' || command == 'insertimage') {
    	url = prompt('Inserte el link: ', '');
    	document.execCommand($(this).data('command'), false, url);
    }else if (command == 'insertimageU') {
    	document.getElementById('imagen').click();

    }else if (command == 'insertVideo') {
    	document.getElementById('video').click();

    }else if (command == 'insertFile') {
    	document.getElementById('archivo').click();

    }else if (command == 'insertFileLink') {
    	document.getElementById('archivoSom').click();

    } else if (command == 'insertVideoYoutube') {
    	
    	url = prompt('Inserte el codigo de insercion de youtube: ', '');
    	document.execCommand("insertHTML", false, url);

    } else document.execCommand($(this).data('command'), false, null);
  });
  
  /**
   * Metodo que permite cargar una imagen en la seccion editable.
   * @returns
   */
  function cargarImagen(){
	  
	  var file    = document.getElementById('imagen').files[0];
	  var reader  = new FileReader();

	  if (file) {
		  reader.onloadend = function () {
			  var image = new Image();

			    image.src = reader.result;

			    image.onload = function() {
			    	
			    	  var ancho = image.width;
					  var id = "rand" + Math.random();
					  var doc = document.getElementById("editor");
					  doc = doc.document;
					  
					  if(ancho <= 700){
						  img = "<img src='" +  image.src + "' id=" + id + ">";
					  }else{
						  img = "<img src='" +  image.src + "' id=" + id + " style='width:700px;height:100%'>";
					  }

					  if(document.all) {
					    var range = doc.selection.createRange();
					    range.pasteHTML(img);
					    range.collapse(false);
					    range.select();
					  } else {
					    document.execCommand("insertHTML", false, img);
					  }  
			    };
		  }
		  
		  reader.readAsDataURL(file);		  
		  document.getElementById("imagen").value = "";
	  }
  }
  
  /**
   * Metodo que coloca el video dentro de la seccion editable.
   * @param input Video a colocar.
   * @returns
   */
  function revisarVideo(input) {
      //THE METHOD THAT SHOULD SET THE VIDEO SOURCE
      if (input.files && input.files[0]) {
    	  var file = input.files[0];
          //var url = URL.createObjectURL(file);
          //console.log(url);
          var reader = new FileReader();
          reader.onload = function() {
        	  var id = "rand" + Math.random();
        	  img = "<video id='" + id + "' height='300' width='600' controls><source src='" +  reader.result + "' /></video>";
        	  document.execCommand("insertHTML", false, img);
          }
          reader.readAsDataURL(file);
          document.getElementById("video").value = "";
      }
  }
  
  /**
   * Metodo que guarda un archivo en base de datos y luego pinta una imagen colocando
   * como direccionamiento el codigo del archivo en base de datos para que cuando le den
   * clic se descargue el archivo.
   * @param input Archivo.
   * @returns 
   */
  function revisarArchivo(input) {
	  // Consulta si cargo algun archivo
    if (input.files && input.files[0]) {
        // Obtiene el archivo
    	var file = input.files[0];    	
        // Creo la url a consumir
		var ruta = document.getElementById("ruta").value;

        var formData = new FormData();
        formData.append('archivo', file);
		document.getElementsByName("tt")[0].setAttribute("id","loader");
		document.getElementById("editor").setAttribute("style","display:none;");
        // Aca guardo el archivo en base de datos y genero un ID
		$.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
		    processData: false,
		    contentType: false,
		    cache: false,
			url : ruta + "/servicios/registrarArchivo",
			data: (formData),
			success : function(result) {
				var id  = result;
				// Si guardo el archivo lo pintamos en pantalla
				pintarImagen(id,file.type);    	
				document.getElementsByName("tt")[0].setAttribute("id","");
		 		document.getElementById("editor").setAttribute("style","block");
			},
			error : function(e) {
				alert("Error! --> "+String(e));
				console.log("ERROR: ", e);
				document.getElementsByName("tt")[0].setAttribute("id","");
		 		document.getElementById("editor").setAttribute("style","block");
			}
		}); 
        
        document.getElementById("archivo").value = "";
    }
}
  
  function pintarImagen(id,fileType){
	  
	  	// Creo la url a consumir

		var ruta = document.getElementById("ruta").value;
        var formData = new FormData();
        formData.append('tipo', fileType);
      	
		// Aca guardo el archivo en base de datos y genero un ID
		$.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
		    processData: false,
		    contentType: false,
		    cache: false,
			url : ruta + "/servicios/solicitarImagen",
			data: (formData),
			success : function(result) {
				var doc = document.getElementById("editor").document;	
				var urlc = url+"/servicios/download?id="+id;
				var img = "<a href=\""+ruta+"/servicios/download?id="+id+"\"><img src='" +  result + "' id=" + id + " height ='50' width ='50' /><a/>";
				document.execCommand("insertHTML", false, img);
				
			},
			error : function(e) {
				alert("Error! --> "+String(e));
				console.log("ERROR: ", e);
			}
		}); 		
  }
  
  function revisarFraseSombreada(input){
	  // Consulta si cargo algun archivo
	    if (input.files && input.files[0]) {
	        // Obtiene el archivo
	    	var file = input.files[0];    	
			var ruta = document.getElementById("ruta").value;

	        var formData = new FormData();
	        formData.append('archivo', file);
			document.getElementsByName("tt")[0].setAttribute("id","loader");
			document.getElementById("editor").setAttribute("style","display:none;");
	        // Aca guardo el archivo en base de datos y genero un ID
			$.ajax({
				type : "POST",
				enctype: 'multipart/form-data',
			    processData: false,
			    contentType: false,
			    cache: false,
				url : ruta + "/servicios/registrarArchivo",
				data: (formData),
				success : function(result) {

					document.getElementsByName("tt")[0].setAttribute("id","");
			 		document.getElementById("editor").setAttribute("style","block"); 	
					var id  = result;
					var urlc = ruta+"/servicios/download?id="+id;
					document.execCommand("createlink", false, urlc);
					
				},
				error : function(e) {
					alert("Error! --> "+String(e));
					console.log("ERROR: ", e);
					document.getElementsByName("tt")[0].setAttribute("id","");
			 		document.getElementById("editor").setAttribute("style","block");
				}
			}); 
	        
	        document.getElementById("archivoSom").value = "";
	    }
  }

  