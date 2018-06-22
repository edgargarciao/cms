package co.ufps.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.ufps.edu.dao.ContactoDao;
import co.ufps.edu.dto.Contacto;

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
public class ContactoController {

  private ContactoDao contactoDao;

  /**
   * Constructor de la clase en donde se inicializan las variables
   */
  public ContactoController() {
	  contactoDao = new ContactoDao();
  }
  
  /**
   * Modelo con el que se realizara el formulario
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("contacto")
  public Contacto setUpUserForm() {
    return new Contacto();
  }
  
  
  /**
   * M�todo que retorna una pagina para realizar el registro de contacto.
   * 
   * @return La p�gina de registro de contactos.
   */
  @GetMapping("/registrarContacto") // Base
  public String registrarContacto(Model model) {
    return "Administrador/Contacto/RegistrarContacto"; // Nombre del archivo jsp
  }
  
  
  /**
   * M�todo que retorna una pagina con todas los contenidos en el sistema.
   * 
   * @return La p�gina principal de contenidos.
   */
  @GetMapping("/contactos") // Base
  public String index(Model model) {
    // Cargamos los contactos para poder mostrarlos en el cuadro.
    model.addAttribute("contactos", contactoDao.getContactos());
    return "Administrador/Contacto/Contactos"; // Nombre del archivo jsp
  }
  
  
  /**
   * Servicio que permite guardar contacto
   * 
   * @param contacto Objeto con la informaci�n a guardar
   * @param model Modelo con la informaci�n necesaria para transportar a los archivos .JSP
   * @return La p�gina a donde debe redireccionar despu�s de la acci�n.
   */
  @PostMapping(value = "/guardarContacto")
  public String registrarContacto(@ModelAttribute("contacto") Contacto contacto, Model model) {

    // Consulta si tiene todos los campos llenos
    if (contacto.isValidoParaRegistrar()) {
      String mensaje = contactoDao.registrarConacto(contacto);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Contacto registrado con �xito.");
        model.addAttribute("contactos", contactoDao.getContactos());
        return "Administrador/Contacto/Contactos"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Contacto/RegistrarContacto";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Contacto/RegistrarContacto";
    }
  }
  
  
  /**
   * M�todo que obtiene la pagina de actualizar contacto dado un ID.
   * 
   * @param idContacto Identificador del contacto
   * @param model Objeto para enviar informaci�n a los archivos .JSP
   * @return La pagina con la informaci�n del contacto cargada.
   */
  @GetMapping(value = "/actualizarContacto")
  public String actualizarContacto(@RequestParam("id") long idContacto, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idContacto <= 0) {
      model.addAttribute("contactos", contactoDao.getContactos());
      return "Administrador/Contacto/Contactos"; // Nombre del archivo jsp
    }
    Contacto contacto = contactoDao.obtenerContactoPorId(idContacto);
    model.addAttribute("contacto", contacto);
    return "Administrador/Contacto/ActualizarContacto"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite editar un contacto.
   * 
   * @param contacto Objeto con la informaci�n a editar.
   * @param model Modelo con la informaci�n necesaria para transportar a los archivos .JSP
   * @return La p�gina a donde debe redireccionar despu�s de la acci�n.
   */
  @PostMapping(value = "/editarContacto")
  public String editarContacto(@ModelAttribute("contacto") Contacto contacto, Model model) {

    // Consulta si tiene todos los campos llenos
    if (contacto.isValidoParaRegistrar()) {
      String mensaje = contactoDao.editarContacto(contacto);
      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", "Informaci�n de contacto actualizada con �xito.");
        model.addAttribute("contactos", contactoDao.getContactos());
        return "Administrador/Contacto/Contactos"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Contacto/ActualizarContacto";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Contacto/ActualizarContacto";
    }
  }
  
  
  /**
   * M�todo que obtiene la pagina de eliminar contacto dado un ID.
   * 
   * @param idContacto Identificador de contacto
   * @param model Objeto para enviar informaci�n a los archivos .JSP
   * @return La pagina con la informaci�n del contacto cargada.
   */
  @GetMapping(value = "/eliminarContacto")
  public String eliminarContacto(@RequestParam("id") long idContacto, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idContacto <= 0) {
      model.addAttribute("contactos", contactoDao.getContactos());
      return "Administrador/Contacto/Contactos"; // Nombre del archivo jsp
    }
    Contacto contacto = contactoDao.obtenerContactoPorId(idContacto);
    model.addAttribute("contacto", contacto);
    return "Administrador/Contacto/EliminarContacto"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite eliminar un contacto.
   * 
   * @param contacto Objeto con la informaci�n a eliminar.
   * @param model Modelo con la informaci�n necesaria para transportar a los archivos .JSP
   * @return La p�gina a donde debe redireccionar despu�s de la acci�n.
   */
  @PostMapping(value = "/borrarContacto")
  public String borrarContacto(@ModelAttribute("contacto") Contacto contacto, Model model) {

    String mensaje = contactoDao.eliminarContacto(contacto);
    if (mensaje.equals("Eliminacion exitosa")) {
      model.addAttribute("result", "Contacto eliminado con �xito.");
      model.addAttribute("contactos", contactoDao.getContactos());
      return "Administrador/Contacto/Contactos"; // Nombre del archivo jsp
    } else {
      model.addAttribute("wrong", mensaje);
      return "Administrador/Contacto/EliminarContacto";
    }

  }
  
}
