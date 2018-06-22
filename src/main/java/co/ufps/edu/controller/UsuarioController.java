package co.ufps.edu.controller;

import javax.servlet.http.HttpServletRequest;
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
import co.ufps.edu.dao.LoginDao;
import co.ufps.edu.dao.UsuarioDao;
import co.ufps.edu.dto.Actividad;
import co.ufps.edu.dto.Usuario;
import co.ufps.edu.util.JwtUtil;

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
public class UsuarioController {

  private UsuarioDao usuarioDao;
  private JwtUtil jwtUtil;
  private LoginDao loginDao;
  
  public UsuarioController() {
    usuarioDao = new UsuarioDao();
    jwtUtil = new JwtUtil();
    loginDao = new LoginDao();
  }
  
  /**
   * Método que retorna una pagina con los datos de la cuenta.
   * 
   * @return La página principal de configuracion.
   */
  @GetMapping("/configuracion") // Base
  public String index(Model model) {
    // Cargamos los contenidos para poder mostrarlas en el cuadro.
    return "Administrador/Cuenta/ActualizarCuenta"; // Nombre del archivo jsp
  }

  /**
   * Modelo con el que se realizara el formulario
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("usuario")
  public Usuario setUpUserForm() {
    return new Usuario();
  }


  /**
   * Servicio que permite guardar una actividad
   * 
   * @param actividad Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/editarCuenta")
  public String registrarActividad(@ModelAttribute("usuario") Usuario usuario, Model model,HttpServletRequest request ) {

    // Consulta si tiene todos los campos llenos
    if (usuario.isValidoParaRegistrar()) {
      String correo = jwtUtil.parseToken(request.getSession().getAttribute("token").toString());
      if(!loginDao.authenticate(correo, usuario.getContrasenaAntigua()).equals("")) {
        if(usuario.seRepiten()) {
          
          String mensaje = usuarioDao.actualizarUsuario(correo,usuario);
          
          if (mensaje.equals("Actualizacion exitosa")) {
            model.addAttribute("result", "Contraseña actualizada con exito.");
            model.addAttribute("usuario", new Usuario());
            return "Administrador/Cuenta/ActualizarCuenta"; // Nombre del archivo jsp
          } else {
            model.addAttribute("wrong", mensaje);
            return "Administrador/Cuenta/ActualizarCuenta"; // Nombre del archivo jsp
          }
        }else {
          model.addAttribute("wrong", "Las nuevas constraseñas deben repetirse.");
          return "Administrador/Cuenta/ActualizarCuenta"; // Nombre del archivo jsp
        }
          //
    } else {
        model.addAttribute("wrong", "La contraseña antigua no es correcta.");
        return "Administrador/Cuenta/ActualizarCuenta"; // Nombre del archivo jsp
      } 
    }else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Cuenta/ActualizarCuenta"; // Nombre del archivo jsp
    }
  }
 
    
}
