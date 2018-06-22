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
import co.ufps.edu.dao.NovedadDao;
import co.ufps.edu.dto.Novedad;



/**
 * Controlador de novedades. Las novedades son sucesos importantes que ocurren y hay que destacarlos para que las 
 * personas puedan observarlo. Todos los servicios publicados en esta clase seran expuestos para ser consumidos 
 * por los archivos .JSP
 * <p>
 * La etiqueta @Controller escanea todos los servicios para publicarlos segun el tipo de metodo
 * HTTP.
 * 
 * @author ufps
 *
 */
@Controller
public class NovedadController {

  @Autowired
  private NovedadDao novedadDao;

  @Autowired
  private ContenidoDao contenidoDao;
  
  
  
  /**
   * Modelo con el que se realizara el formulario
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("novedad")
  public Novedad setUpUserForm() {
    return new Novedad();
  }
  
  /**
   * Método que retorna una pagina con todas las novedades en el sistema.
   * 
   * @return La página principal de novedades.
   */
  @GetMapping("/novedades") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    model.addAttribute("novedades", novedadDao.getNovedades());
    return "Administrador/Novedad/novedades"; // Nombre del archivo jsp
  }
  
  /**
   * Método que retorna una pagina para realizar el registro de novedad.
   * 
   * @return La página de registro de novedades.
   */
  @GetMapping("/registrarNovedad") // Base
  public String registrarNovedad(Model model) {
    return "Administrador/Novedad/RegistrarNovedad"; // Nombre del archivo jsp
  }
  
  /**
   * Servicio que permite guardar novedad
   * 
   * @param novedad Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/guardarNovedad")
  public String guardarNovedad(@ModelAttribute("novedad") Novedad novedad, Model model) {

    // Consulta si tiene todos los campos llenos
    if (novedad.isValidoParaRegistrar()) {
      String mensaje = "Registro exitoso";
      novedadDao.registrarNovedad(novedad);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Novedad registrada con éxito.");
        model.addAttribute("novedades", novedadDao.getNovedades());
        return "Administrador/Novedad/novedades"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Novedad/RegistrarNovedad";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenartodos  los campos.");
      return "Administrador/Novedad/RegistrarNovedad";
    }
  }
  
  /**
   * Método que obtiene la pagina de actualizar novedad dado un ID.
   * 
   * @param idNovedad Identificador de la novedad
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la novedad cargada.
   */
  @GetMapping(value = "/actualizarNovedad")
  public String actualizarNovedad(@RequestParam("id") long idNovedad, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idNovedad <= 0) {
      model.addAttribute("novedades", novedadDao.getNovedades());
      return "Administrador/Novedad/novedades"; // Nombre del archivo jsp
    }
    Novedad novedad = novedadDao.obtenerNovedadPorId(idNovedad);
    model.addAttribute("novedad", novedad);
    return "Administrador/Novedad/ActualizarNovedad"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite editar una novedad.
   * 
   * @param novedad Objeto con la información a editar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/editarNovedad")
  public String editarNovedad(@ModelAttribute("novedad") Novedad novedad, Model model) {

    // Consulta si tiene todos los campos llenos
    if (novedad.isValidoParaActualizar()) {
      String mensaje = "Actualizacion exitosa"; 
      novedadDao.editarNovedad(novedad);
      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", "Información de novedad actualizada con éxito.");
        model.addAttribute("novedades", novedadDao.getNovedades());
        return "Administrador/Novedad/novedades"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);        
        return "Administrador/Novedad/ActualizarNovedad";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Novedad/ActualizarNovedad";
    }
  }
  
  /**
   * Método que obtiene la pagina de eliminar novedad dado un ID.
   * 
   * @param idNovedad Identificador de la novedad
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la novedad cargada.
   */
  @GetMapping(value = "/eliminarNovedad")
  public String eliminarNovedad(@RequestParam("id") long idNovedad, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idNovedad <= 0) {
      model.addAttribute("novedades", novedadDao.getNovedades());
      return "Administrador/Novedad/novedades"; // Nombre del archivo jsp
    }
    Novedad novedad = novedadDao.obtenerNovedadPorId(idNovedad);
    model.addAttribute("novedad", novedad);
    return "Administrador/Novedad/EliminarNovedad"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite eliminar una novedad.
   * 
   * @param novedad Objeto con la información a eliminar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/borrarNovedad")
  public String borrarNovedad(@ModelAttribute("novedad") Novedad novedad, Model model) {
    if(!contenidoDao.tieneContenido(novedad.getId(),Constantes.NOVEDAD)) {
      String mensaje = novedadDao.eliminarNovedad(novedad);
  
      if (mensaje.equals("Eliminacion exitosa")) {
        model.addAttribute("result", "Novedad eliminada con éxito.");
        model.addAttribute("novedades", novedadDao.getNovedades());
        return "Administrador/Novedad/novedades"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Novedad/EliminarNovedad";
      }
    }else {
      model.addAttribute("wrong", "No es posible eliminar la novedad debido a que tiene un contenido registrado.");
      return "Administrador/Novedad/EliminarNovedad";
    }
  }

}
