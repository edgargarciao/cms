package co.ufps.edu.config;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import co.ufps.edu.constantes.Constantes;
import co.ufps.edu.util.JwtUtil;

/**
 * Clase que permite manejar las sesiones del sistema.
 * @author edgar
 *
 */
public class SessionManager implements HandlerInterceptor {

  private JwtUtil jwtUtil;
  public HashMap<String, String> sesiones;

  /**
   * Constructor de la clase que inicializa las variables
   */
  public SessionManager() {
    sesiones = new HashMap<>();
    jwtUtil = new JwtUtil();
  }

  /**
   * Metodo que guarda una sesion en el sistema
   * @param sesion
   * @param token
   */
  public void guardarSession(String sesion, String token) {
    sesiones.put(sesion, token);
  }
  
  public void eliminarSesion(String sesion) {
    sesiones.remove(sesion);
  }

  // This method is called before the controller
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    
     request.setCharacterEncoding("UTF-8");
    
    if(request.getSession().getAttribute("token") == null)
    {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.sendRedirect(Constantes.RUTA+"/admin");
      return false;
    }
    else {
      // Se extrae el token de sesion
      String token = request.getSession().getAttribute("token").toString();
      
      // Se valida el token
      boolean permission = validarToken(token);
      
      // Si el token es valido se permite la continuidad
      if (permission) {
        return true;
      // Si el token no es valido se envia un mensaje de no autorizado. 
      } else {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendRedirect(Constantes.SERVER+Constantes.RUTA+"/admin");
        return false;
        // Above code will send a 401 with no response body.
        // If you need a 401 view, do a redirect instead of
        // returning false.
        // response.sendRedirect("/401"); // assuming you have a handler mapping for 401
  
      }
    }
  }

  private boolean validarToken(String token) {
    String correo = jwtUtil.parseToken(token);
    if (token == null || token.isEmpty() || StringUtils.isEmpty(correo)
        || sesiones.get("SESSION:" + correo) == null) {
      return false;
    }
    
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

  }
}
