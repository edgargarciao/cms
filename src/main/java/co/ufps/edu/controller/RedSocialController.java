package co.ufps.edu.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import co.ufps.edu.dao.RedSocialDao;
import co.ufps.edu.dto.RedSocial;

/**
 * Controlador de contenidos. Los contenidos son las paginas que se abren cuando se da click a una informaci�n. Todos los
 * servicios publicados en esta clase seran expuestos para ser consumidos por los archivos .JSP
 * <p>
 * La etiqueta @Controller escanea todos los servicios para publicarlos segun el tipo de metodo
 * HTTP.
 * 
 * @author ufps
 *
 */
@Controller
public class RedSocialController {

	
	private RedSocialDao redSocialDao;

  /**
   * Constructor de la clase en donde se inicializan las variables
   */
  public RedSocialController() {
	  redSocialDao = new RedSocialDao();
  }
  
  
  /**
   * Modelo con el que se realizara el formulario
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("redSocial")
  public RedSocial setUpUserForm() {
    return new RedSocial();
  }
  
  /**
   * M�todo que retorna una pagina con todas los redes sociales en el sistema.
   * 
   * @return La p�gina principal de redes sociales.
   */
  @GetMapping("/redes") // Base
  public String index(Model model) {
    // Cargamos las redes sociales para poder mostrarlas en el cuadro.
    model.addAttribute("redesSociales", redSocialDao.getRedesSociales());
    return "Administrador/RedSocial/redes"; // Nombre del archivo jsp
  }
  
  /**
   * M�todo que retorna una pagina para realizar el registro de una red social.
   * 
   * @return La p�gina de registro de redes sociales.
   */
  @GetMapping("/registrarRedSocial") // Base
  public String registrarRedSocial(Model model) {
    return "Administrador/RedSocial/RegistrarRedSocial"; // Nombre del archivo jsp
  }
  


  /**
   * Servicio que permite guardar red social
   * 
   * @param redSocial Objeto con la informaci�n a guardar
   * @param model Modelo con la informaci�n necesaria para transportar a los archivos .JSP
   * @return La p�gina a donde debe redireccionar despu�s de la acci�n.
   */
  @PostMapping(value = "/guardarRedSocial")
  public String registrarRedSocial(@ModelAttribute("redSocial") RedSocial redSocial, Model model) {
    // Consulta si tiene todos los campos llenos
    if (redSocial.isValidoParaRegistrar()) {
      String mensaje = redSocialDao.registrarRedSocial(redSocial);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Red social registrada con �xito.");
        model.addAttribute("redesSociales", redSocialDao.getRedesSociales());
        return "Administrador/RedSocial/redes"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/RedSocial/RegistrarRedSocial";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/RedSocial/RegistrarRedSocial";
    }
  }
  
  
  /**
   * M�todo que obtiene la pagina de actualizar red social dado un ID.
   * 
   * @param idRedSocial Identificador de la red social
   * @param model Objeto para enviar informaci�n a los archivos .JSP
   * @return La pagina con la informaci�n de la red social cargada.
   */
  @GetMapping(value = "/actualizarRedSocial")
  public String actualizarRedSocial(@RequestParam("id") long idRedSocial, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idRedSocial <= 0) {
    	model.addAttribute("redesSociales", redSocialDao.getRedesSociales());
    	return "Administrador/RedSocial/redes"; // Nombre del archivo jsp
    }
    RedSocial redSocial = redSocialDao.obtenerRedSocialPorId(idRedSocial);
    model.addAttribute("redSocial", redSocial);
    return "Administrador/RedSocial/ActualizarRedSocial"; // Nombre del archivo jsp
  }
  
  /**
   * Servicio que permite editar una red social.
   * 
   * @param redSocial Objeto con la informaci�n a editar.
   * @param model Modelo con la informaci�n necesaria para transportar a los archivos .JSP
   * @return La p�gina a donde debe redireccionar despu�s de la acci�n.
   */
  @PostMapping(value = "/editarRedSocial")
  public String editarRedSocial(@ModelAttribute("redSocial") RedSocial redSocial, Model model) {

    // Consulta si tiene todos los campos llenos
    if (redSocial.isValidoParaRegistrar()) {
      String mensaje = redSocialDao.editarRedSocial(redSocial);
      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", "Informaci�n de red social actualizada con �xito.");
        model.addAttribute("redesSociales", redSocialDao.getRedesSociales());
        return "Administrador/RedSocial/redes"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/RedSocial/ActualizarRedSocial";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/RedSocial/ActualizarRedSocial";
    }
  }
  
  /**
   * M�todo que obtiene la pagina de eliminar red social dado un ID.
   * 
   * @param idRedSocial Identificador de red social
   * @param model Objeto para enviar informaci�n a los archivos .JSP
   * @return La pagina con la informaci�n de la red social cargada.
   */
  @GetMapping(value = "/eliminarRedSocial")
  public String eliminarRedSocial(@RequestParam("id") long idRedSocial, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idRedSocial <= 0) {
    	model.addAttribute("redesSociales", redSocialDao.getRedesSociales());
      return "Administrador/RedSocial/redes"; // Nombre del archivo jsp
    }
    RedSocial redSocial = redSocialDao.obtenerRedSocialPorId(idRedSocial);
    model.addAttribute("redSocial", redSocial);
    return "Administrador/RedSocial/EliminarRedSocial"; // Nombre del archivo jsp
  }

 
  /**
   * Servicio que permite eliminar una red social.
   * 
   * @param redSocial Objeto con la informaci�n a eliminar.
   * @param model Modelo con la informaci�n necesaria para transportar a los archivos .JSP
   * @return La p�gina a donde debe redireccionar despu�s de la acci�n.
   */
  @PostMapping(value = "/borrarRedSocial")
  public String borrarRedSocial(@ModelAttribute("redSocial") RedSocial redSocial, Model model) {

    String mensaje = redSocialDao.eliminarRedSocial(redSocial);
    if (mensaje.equals("Eliminacion exitosa")) {
      model.addAttribute("result", "Red social eliminada con �xito.");
      model.addAttribute("redesSociales", redSocialDao.getRedesSociales());
      return "Administrador/RedSocial/redes"; // Nombre del archivo jsp
    } else {
      model.addAttribute("wrong", mensaje);
      return "Administrador/RedSocial/EliminarRedSocial";
    }

  }
  
}
