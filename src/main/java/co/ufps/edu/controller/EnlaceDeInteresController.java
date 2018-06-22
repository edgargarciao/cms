package co.ufps.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import co.ufps.edu.dao.EnlaceDeInteresDao;
import co.ufps.edu.dto.EnlaceDeInteres;

/**
 * Controlador de contenidos. Los contenidos son las paginas que se abren cuando se da click a una
 * información. Todos los servicios publicados en esta clase seran expuestos para ser consumidos por
 * los archivos .JSP
 * <p>
 * La etiqueta @Controller escanea todos los servicios para publicarlos segun el tipo de metodo
 * HTTP.
 * 
 * @author ufps
 *
 */
@Controller
public class EnlaceDeInteresController {

  private EnlaceDeInteresDao enlaceDeInteresDao;

  /**
   * Constructor de la clase en donde se inicializan las variables
   */
  public EnlaceDeInteresController() {
    enlaceDeInteresDao = new EnlaceDeInteresDao();
  }


  /**
   * Modelo con el que se realizara el formulario
   * 
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("enlaceDeInteres")
  public EnlaceDeInteres setUpUserForm() {
    return new EnlaceDeInteres();
  }


  /**
   * Método que retorna una pagina con todas los enlaces de interes en el sistema.
   * 
   * @return La página principal de enlaces de interes.
   */
  @GetMapping("/enlacesDeInteres") // Base
  public String index(Model model) {
    // Cargamos los enlaces de interes para poder mostrarlos en el cuadro.
    model.addAttribute("enlacesDeInteres", enlaceDeInteresDao.getEnlacesDeInteres());
    return "Administrador/EnlaceDeInteres/EnlacesDeInteres"; // Nombre del archivo jsp
  }


  /**
   * Método que retorna una pagina para realizar el registro de enlaces de interes.
   * 
   * @return La página de registro de enlaces de interes.
   */
  @GetMapping("/registrarEnlaceDeInteres") // Base
  public String registrarEnlaceDeIneres(Model model) {
    return "Administrador/EnlaceDeInteres/RegistrarEnlaceDeInteres"; // Nombre del archivo jsp
  }


  /**
   * Servicio que permite guardar enlace de interes
   * 
   * @param enlaceDeInteres Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/guardarEnlaceDeInteres")
  public String registrarEnlaceDeInteres(
      @ModelAttribute("enlaceDeInteres") EnlaceDeInteres enlaceDeInteres, Model model) {

    // Consulta si tiene todos los campos llenos
    if (enlaceDeInteres.isValidoParaRegistrar()) {
      String mensaje = enlaceDeInteresDao.registrarEnlaceDeInteres(enlaceDeInteres);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Enlace de interes registrado con éxito.");
        model.addAttribute("enlacesDeInteres", enlaceDeInteresDao.getEnlacesDeInteres());
        return "Administrador/EnlaceDeInteres/EnlacesDeInteres"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/EnlaceDeInteres/RegistrarEnlaceDeInteres";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/EnlaceDeInteres/RegistrarEnlaceDeInteres";
    }
  }


  /**
   * Método que obtiene la pagina de actualizar enlace de interes dado un ID.
   * 
   * @param idEnlaceDeInteres Identificador del enlace de interes
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información del enlace de interes cargado.
   */
  @GetMapping(value = "/actualizarEnlaceDeInteres")
  public String actualizarEnlaceDeInteres(@RequestParam("id") long idEnlaceDeInteres, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idEnlaceDeInteres <= 0) {
      model.addAttribute("enlacesDeInteres", enlaceDeInteresDao.getEnlacesDeInteres());
      return "Administrador/EnlaceDeInteres/EnlacesDeInteres"; // Nombre del archivo jsp
    }
    EnlaceDeInteres enlaceDeInteres =
        enlaceDeInteresDao.obtenerEnlaceDeInteresPorId(idEnlaceDeInteres);
    model.addAttribute("enlaceDeInteres", enlaceDeInteres);
    return "Administrador/EnlaceDeInteres/ActualizarEnlaceDeInteres"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite editar un enlace de interes.
   * 
   * @param enlaceDeInteres Objeto con la información a editar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/editarEnlaceDeInteres")
  public String editarEnlaceDeInteres(
      @ModelAttribute("enlaceDeInteres") EnlaceDeInteres enlaceDeInteres, Model model) {

    // Consulta si tiene todos los campos llenos
    if (enlaceDeInteres.isValidoParaRegistrar()) {
      String mensaje = enlaceDeInteresDao.editarEnlaceDeInteres(enlaceDeInteres);
      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", "Información de enlace de interes actualizada con éxito.");
        model.addAttribute("enlacesDeInteres", enlaceDeInteresDao.getEnlacesDeInteres());
        return "Administrador/EnlaceDeInteres/EnlacesDeInteres"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/EnlaceDeInteres/ActualizarEnlaceDeInteres";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/EnlaceDeInteres/ActualizarEnlaceDeInteres";
    }
  }


  /**
   * Método que obtiene la pagina de eliminar enlace de interes dado un ID.
   * 
   * @param idEnlaceDeInteres Identificador de enlace de interes
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información del enlace de interes cargado.
   */
  @GetMapping(value = "/eliminarEnlaceDeInteres")
  public String eliminarEnlaceDeInteres(@RequestParam("id") long idEnlaceDeInteres, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idEnlaceDeInteres <= 0) {
      model.addAttribute("enlacesDeInteres", enlaceDeInteresDao.getEnlacesDeInteres());
      return "Administrador/EnlaceDeInteres/EnlacesDeInteres"; // Nombre del archivo jsp
    }
    EnlaceDeInteres enlaceDeInteres =
        enlaceDeInteresDao.obtenerEnlaceDeInteresPorId(idEnlaceDeInteres);
    model.addAttribute("enlaceDeInteres", enlaceDeInteres);
    return "Administrador/EnlaceDeInteres/EliminarEnlaceDeInteres"; // Nombre del archivo jsp
  }


  /**
   * Servicio que permite eliminar un enlace de interes.
   * 
   * @param enlaceDeInteres Objeto con la información a eliminar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/borrarEnlaceDeInteres")
  public String borrarEnlaceDeInteres(
      @ModelAttribute("enlaceDeInteres") EnlaceDeInteres enlaceDeInteres, Model model) {

    String mensaje = enlaceDeInteresDao.eliminarEnlaceDeInteres(enlaceDeInteres);
    if (mensaje.equals("Eliminacion exitosa")) {
      model.addAttribute("result", "EnlaceDeInteres eliminado con éxito.");
      model.addAttribute("enlacesDeInteres", enlaceDeInteresDao.getEnlacesDeInteres());
      return "Administrador/EnlaceDeInteres/EnlacesDeInteres"; // Nombre del archivo jsp
    } else {
      model.addAttribute("wrong", mensaje);
      return "Administrador/EnlaceDeInteres/EliminarEnlaceDeInteres";
    }

  }

}
