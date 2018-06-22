// Evento de Jquery para que realice la función recien inicie la pagina.
$(document).ready(function(){

  // Nombre del namespace For que se encuentra en los campos de fecha.
  let nombreAtributoFor = "date"; 

  // Obtenemos el campo de texto de donde se desplegara el calendario.
  var date_input = $('input[name="' + nombreAtributoFor + '"]');  

  /**
   * Aqui se realiza un corto circuito en donde se pregunta si ya existe una etiqueta bootstrap-iso,
   * si asi fuera retornaria la etiqueta padre de ella, pero como no existe, retorna "body"
   */
  var contenedor = ($('.bootstrap-iso form').length > 0) ? $('.bootstrap-iso form').parent() : "body";

  /**
   * Vector con cuatro parametros
   * 1. Formato de fecha.
   * 2. Contenedor en donde sera colocado el texto.
   * 3. Activando el "todayHighlight" podemos obtener 
   *    la sombra cuando paseamos por los numeros del
   *    calendario.
   * 4. Activando el "autoclose" permite cerrar el componente
   *    recien seleccionamos una fecha, estando en false el
   *    componente se queda hasta que des click en otro lado.
   */
  var opciones = {
    format: 'dd/mm/yyyy',
    container: contenedor,
    todayHighlight: true,
    autoclose: true,
  };

  // Dibuja el componente de acuerdo a las opciones.
  date_input.datepicker(opciones);
})