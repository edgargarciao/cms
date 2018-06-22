package co.ufps.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import co.ufps.edu.constantes.Constantes;
import co.ufps.edu.dao.ActividadDao;
import co.ufps.edu.dao.ContenidoDao;
import co.ufps.edu.dto.Actividad;

/**
 * Controlador de actividades. Las actividades son los eventos programador y que seran realizados en una fecha especifica. 
 * Todos los servicios publicados en esta clase seran expuestos para ser consumidos por los archivos .JSP
 * <p>
 * La etiqueta @Controller escanea todos los servicios para publicarlos segun el tipo de metodo
 * HTTP.
 * 
 * @author ufps
 *
 */
@Controller
public class ActividadController {

  @Autowired
  private ActividadDao actividadDao;

  @Autowired
  private ContenidoDao contenidoDao;
  
  /**
   * Método que retorna una pagina con todas los contenidos en el sistema.
   * 
   * @return La página principal de contenidos.
   */
  @GetMapping("/actividades") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    model.addAttribute("actividades", actividadDao.getActividades());
    return "Administrador/Actividad/Actividades"; // Nombre del archivo jsp
  }

  /**
   * Modelo con el que se realizara el formulario
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("actividad")
  public Actividad setUpUserForm() {
    return new Actividad();
  }

  /**
   * Método que retorna una pagina para realizar el registro de una actividad.
   * 
   * @return La página de registro de actividades.
   */
  @GetMapping("/registrarActividad") // Base
  public String registrarActividad() {
    return "Administrador/Actividad/RegistrarActividad"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite guardar una actividad
   * 
   * @param actividad Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/guardarActividad")
  public String registrarActividad(@ModelAttribute("actividad") Actividad actividad, Model model) {

    // Consulta si tiene todos los campos llenos
    if (actividad.isValidoParaRegistrar()) {
      if(!actividad.HaySolapamiento()) {
        String mensaje = actividadDao.registrarActividad(actividad);
        ;
        if (mensaje.equals("Registro exitoso")) {
          model.addAttribute("result", "Actividad registrada con éxito.");
          model.addAttribute("actividades", actividadDao.getActividades());
          return "Administrador/Actividad/Actividades"; // Nombre del archivo jsp
        } else {
          model.addAttribute("wrong", mensaje);
          return "Administrador/Actividad/RegistrarActividad"; // Nombre del archivo jsp
        }
      }else {
        model.addAttribute("wrong", "Las fechas no pueden solaparse.");
        return "Administrador/Actividad/RegistrarActividad"; // Nombre del archivo jsp
      }
        //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Actividad/RegistrarActividad"; // Nombre del archivo jsp
    }
  }
 
  /**
   * Método que obtiene la pagina de actualizar actividad dado un ID.
   * 
   * @param idActualizar Identificador de la actividad
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la actividad cargada.
   */
  @GetMapping(value = "/actualizarActividad")
  public String actualizarActividad(@RequestParam("id") long idActividad, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idActividad <= 0) {
      model.addAttribute("actividades", actividadDao.getActividades());
      return "Administrador/Actividad/Actividades"; // Nombre del archivo jsp
    }
    Actividad actividad = actividadDao.obtenerActividadPorId(idActividad);
    model.addAttribute("actividad", actividad);
    return "Administrador/Actividad/ActualizarActividad"; // Nombre del archivo jsp
  }  
  
  /**
   * Servicio que permite editar una actividad.
   * 
   * @param actividad Objeto con la información a editar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/editarActividad")
  public String editarActividad(@ModelAttribute("actividad") Actividad actividad, Model model) {

    // Consulta si tiene todos los campos llenos
    if (actividad.isValidoParaActualizar()) {
      if(!actividad.HaySolapamiento()) {  
        String mensaje = actividadDao.editarActividad(actividad);
        
        if (mensaje.equals("Actualizacion exitosa")) {
          model.addAttribute("result", "Actividad actualizada con éxito.");
          model.addAttribute("actividades", actividadDao.getActividades());
          return "Administrador/Actividad/Actividades"; // Nombre del archivo jsp
        } else {
          model.addAttribute("wrong", mensaje);
          return "Administrador/Actividad/ActualizarActividad";
        }
      }else{
        model.addAttribute("wrong", "Las fechas no pueden solaparse.");
        return "Administrador/Actividad/RegistrarActividad"; // Nombre del archivo jsp
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Actividad/ActualizarActividad";
    }
  }    
  
  /**
   * Método que obtiene la pagina de eliminar actividad dado un ID.
   * 
   * @param idActividad Identificador de la actividad
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la actividad cargada.
   */
  @GetMapping(value = "/eliminarActividad")
  public String eliminarActividad(@RequestParam("id") long idActividad, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idActividad <= 0) {
      model.addAttribute("actividades", actividadDao.getActividades());
      return "Administrador/Actividad/Actividades"; // Nombre del archivo jsp
    }
    Actividad actividad = actividadDao.obtenerActividadPorId(idActividad);
    model.addAttribute("actividad", actividad);
    return "Administrador/Actividad/EliminarActividad"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite eliminar una actividad.
   * 
   * @param actividad Objeto con la información a eliminar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/borrarActividad")
  public String borrarActividad(@ModelAttribute("actividad") Actividad actividad, Model model) {
    if(!contenidoDao.tieneContenido(actividad.getId(),Constantes.ACTIVIDAD)) {
      String mensaje = actividadDao.eliminarActividad(actividad);
      if (mensaje.equals("Eliminacion exitosa")) {
        model.addAttribute("result", "Actividad eliminada con éxito.");
        model.addAttribute("actividades", actividadDao.getActividades());
        return "Administrador/Actividad/Actividades"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Actividad/EliminarActividad";
      }
    }else {
      model.addAttribute("wrong", "No es posible eliminar la actividad debido a que tiene un contenido registrado.");
      return "Administrador/Actividad/EliminarActividad";
    }
  }
  
}
