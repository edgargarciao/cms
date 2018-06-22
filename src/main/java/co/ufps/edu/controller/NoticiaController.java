package co.ufps.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import co.ufps.edu.constantes.Constantes;
import co.ufps.edu.dao.ContenidoDao;
import co.ufps.edu.dao.NoticiaDao;
import co.ufps.edu.dto.Noticia;
import co.ufps.edu.dto.SubCategoria;

/**
 * Controlador de noticias. Las noticias son publicaciones que permiten informar a las personas de los
 * sucesos que acontecen. Todos los servicios publicados en esta clase seran expuestos para ser 
 * consumidos por los archivos .JSP
 * <p>
 * La etiqueta @Controller escanea todos los servicios para publicarlos segun el tipo de metodo
 * HTTP.
 * 
 * @author ufps
 *
 */
@Controller
public class NoticiaController {

  @Autowired
  private NoticiaDao noticiaDao;

  @Autowired
  private ContenidoDao contenidoDao;

  /**
   * Método que retorna una pagina con todas las noticias en el sistema.
   * 
   * @return La página principal de noticias.
   */
  @GetMapping("/noticias") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    model.addAttribute("noticias", noticiaDao.getNoticias());
    return "Administrador/Noticia/Noticias"; // Nombre del archivo jsp
  }
  
  /**
   * Modelo con el que se realizara el formulario
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("noticia")
  public Noticia setUpUserForm() {
    return new Noticia();
  }  
  
  /**
   * Método que retorna una pagina para realizar el registro de una noticia.
   * 
   * @return La página de registro de noticias.
   */
  @GetMapping("/registrarNoticia") // Base
  public String registrarNoticia() {
    return "Administrador/Noticia/RegistrarNoticia"; // Nombre del archivo jsp
  }
  
  /**
   * Servicio que permite guardar una noticia
   * 
   * @param noticia Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/guardarNoticia")
  public String registrarNoticia(@ModelAttribute("noticia") Noticia noticia, Model model) {

    // Consulta si tiene todos los campos llenos
    if (noticia.isValidoParaRegistrar()) {
      noticiaDao.cambiarOrden();
      
      String mensaje = "Registro exitoso";
      noticiaDao.registrarNoticia(noticia);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Noticia registrada con éxito.");
        model.addAttribute("noticias", noticiaDao.getNoticias());
        return "Administrador/Noticia/Noticias"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Noticia/RegistrarNoticia"; // Nombre del archivo jsp
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Noticia/RegistrarNoticia"; // Nombre del archivo jsp
    }
  }
  
  /**
   * Servicio que permite bajar el numero de orden de una noticia
   * 
   * @param idNoticia Idenrificador de la noticia
   * @param orden Numero de orden actual
   * @param model Donde se cargaran las noticias actualizadas
   * @return El redireccionamiento a la pagina de noticias
   */
  @GetMapping(value = "/bajarOrdenNoticia")
  public String bajarOrdenDeNoticia(@RequestParam("id") long idNoticia,
      @RequestParam("orden") int orden, Model model) {
    // Cambiar orden
    bajarOrden(idNoticia, orden);

    // Cargar noticias en model
    model.addAttribute("noticias", noticiaDao.getNoticias());

    return "Administrador/Noticia/Noticias";
  }

  /**
   * Metodo que baja de orden una noticia
   * 
   * @param idNoticia Identificador de la noticia
   * @param orden Numero de orden
   */
  private void bajarOrden(long idNoticia, int orden) {

    // Consulto que el Id sea mayor a 0.
    if (idNoticia <= 0) {
      return;
    }

    // Obtengo el menor número de ordenamiento
    int ordenMaximo = noticiaDao.getUltimoNumeroDeOrden();

    // Si el numero de orden es el máximo es por que ya es el ultimo y no se debe hacer nada.
    if (orden == ordenMaximo) {
      return;
      // Cambio el orden
    } else {
      noticiaDao.descenderOrden(idNoticia, orden);
    }
  }  
  
  /**
   * Servicio que permite subir el numero de orden de una noticia
   * 
   * @param idNoticia Idenrificador de la noticia
   * @param orden Numero de orden actual
   * @param model Donde se cargaran las noticias actualizadas
   * @return El redireccionamiento a la pagina de noticias
   */
  @GetMapping(value = "/subirOrdenNoticia")
  public String subirOrdenDeNoticia(@RequestParam("id") long idNoticia,
      @RequestParam("orden") int orden, Model model) {
    // Cambiar orden
    subirOrden(idNoticia, orden);

    // Cargar noticias en model
    model.addAttribute("noticias", noticiaDao.getNoticias());

    return "Administrador/Noticia/Noticias";
  }

  /**
   * Metodo que permite subir una noticia de orden
   * 
   * @param idNoticia Identificador de la noticia.
   * @param orden Orden de la noticia.
   */
  private void subirOrden(long idNoticia, int orden) {

    // Consulto que el Id sea mayor a 0.
    if (idNoticia <= 0) {
      return;
    }

    // Si el numero de orden es el minimo es por que ya es el primero y no se debe hacer nada.
    if (orden == 1) {
      return;
      // Cambio el orden
    } else {
      noticiaDao.ascenderOrden(idNoticia, orden);
    }
  }  
  
  
  /**
   * Método que obtiene la pagina de actualizar noticia dado un ID.
   * 
   * @param idNoticia Identificador de la noticia
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la noticia cargada.
   */
  @GetMapping(value = "/actualizarNoticia")
  public String actualizarnoticia(@RequestParam("id") long idNoticia, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idNoticia <= 0) {
      model.addAttribute("noticias", noticiaDao.getNoticias());
      return "Administrador/Noticia/Noticias"; // Nombre del archivo jsp
    }
    Noticia noticia = noticiaDao.obtenerNoticiaPorId(idNoticia);
    model.addAttribute("noticia", noticia);
    return "Administrador/Noticia/ActualizarNoticia"; // Nombre del archivo jsp
  }  
  
  /**
   * Servicio que permite editar una noticia.
   * 
   * @param noticia Objeto con la información a editar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/editarNoticia")
  public String editarNoticia(@ModelAttribute("noticia") Noticia noticia, Model model) {

    // Consulta si tiene todos los campos llenos
    if (noticia.isValidoParaActualizar()) {
      String mensaje = "Actualizacion exitosa";
      noticiaDao.editarNoticia(noticia);
      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", "Noticia actualizada con éxito.");
        model.addAttribute("noticias", noticiaDao.getNoticias());
        return "Administrador/Noticia/Noticias"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        Noticia noti = noticiaDao.obtenerNoticiaPorId(noticia.getId());
        Noticia no = (Noticia) model.asMap().get("noticia");
        no.setIm1Base64image(noti.getIm1Base64image());
        return "Administrador/Noticia/ActualizarNoticia";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      Noticia noti = noticiaDao.obtenerNoticiaPorId(noticia.getId());
      Noticia no = (Noticia) model.asMap().get("noticia");
      no.setIm1Base64image(noti.getIm1Base64image());
      return "Administrador/Noticia/ActualizarNoticia";
    }
  }  
  
  
  /**
   * Método que obtiene la pagina de actualizar noticia dado un ID.
   * 
   * @param idNoticia Identificador de la noticia
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la noticia cargada.
   */
  @GetMapping(value = "/eliminarNoticia")
  public String eliminarnoticia(@RequestParam("id") long idNoticia, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idNoticia <= 0) {
      model.addAttribute("noticias", noticiaDao.getNoticias());
      return "Administrador/Noticia/Noticias"; // Nombre del archivo jsp
    }
    Noticia noticia = noticiaDao.obtenerNoticiaPorId(idNoticia);
    model.addAttribute("noticia", noticia);
    return "Administrador/Noticia/EliminarNoticia"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite eliminar una noticia.
   * 
   * @param noticia Objeto con la información a eliminar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/borrarNoticia")
  public String borrarnoticia(@ModelAttribute("noticia") Noticia noticia, Model model) {
    if(!contenidoDao.tieneContenido(noticia.getId(),Constantes.NOTICIA)) {
      String mensaje = noticiaDao.eliminarNoticia(noticia);
      cambiarOrdenDeNoticias(noticia);
      if (mensaje.equals("Eliminacion exitosa")) {
        model.addAttribute("result", "Noticia eliminada con éxito.");
        model.addAttribute("noticias", noticiaDao.getNoticias());
        return "Administrador/Noticia/Noticias"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Noticia/EliminarNoticia";
      }
    }else {
      model.addAttribute("wrong", "No es posible eliminar la noticia debido a que tiene un contenido registrado.");
      return "Administrador/Noticia/EliminarNoticia";
    }

  }

  /**
   * Metodo que reordena todas las noticias dado un orden faltante.
   * @param noticia Objeto con la información de la noticia borrada.
   */
  private void cambiarOrdenDeNoticias(Noticia noticia) {
    // Obtengo el menor número de ordenamiento
    int ordenMaximo = noticiaDao.getUltimoNumeroDeOrden();
    
    for(int i = noticia.getOrden();i<ordenMaximo;i++) {
      long idnoticia = noticiaDao.getIdNoticiaPorOrden(i+1);
      noticiaDao.cambiarOrdenDeNoticia(idnoticia, i);
    }
  }
  

}
