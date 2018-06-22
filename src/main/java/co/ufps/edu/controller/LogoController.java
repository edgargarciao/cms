package co.ufps.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import co.ufps.edu.dao.LogoDao;
import co.ufps.edu.dto.Logo;

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
public class LogoController {

  
  @Autowired
  private LogoDao logoDao;

  /**
   * Método que retorna una pagina con todas los contenidos en el sistema.
   * 
   * @return La página principal de contenidos.
   */
  @GetMapping("/logos") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    model.addAttribute("logoHorizontal", logoDao.getLogo("LogoHorizontal"));
    model.addAttribute("logoVertical", logoDao.getLogo("LogoVertical"));
    return "Administrador/Logo/logos"; // Nombre del archivo jsp
  }

  /**
   * Modelo con el que se realizara el formulario
   * 
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("logo")
  public Logo setUpUserForm() {
    return new Logo();
  }
  
  /**
   * Servicio que permite guardar un logo
   * 
   * @param logo Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/guardarLogo")
  public String guardarLogoHorizontal(@ModelAttribute("logo") Logo logo, Model model) {

      if(logo.getId() == 0) {
        String mensaje = logoDao.insertarLogo(logo);

        if (mensaje.equals("Registro exitoso")) {
          model.addAttribute("result", "Logo registrado con éxito.");
        } else {
          model.addAttribute("wrong", mensaje);
        }
      }else {
        
        String mensaje =logoDao.actualizarLogo(logo);
        
        if (mensaje.equals("Actualizacion exitosa")) {
          model.addAttribute("result", "Logo actualizado con éxito.");
        } else {
          model.addAttribute("wrong", mensaje);
        }
      }
      
      return index(model);

  }
}
