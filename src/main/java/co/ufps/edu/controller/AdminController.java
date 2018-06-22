package co.ufps.edu.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import co.ufps.edu.config.SessionManager;
import co.ufps.edu.dao.ActividadDao;
import co.ufps.edu.dao.CategoriaDao;
import co.ufps.edu.dao.ComponenteDao;
import co.ufps.edu.dao.ContactoDao;
import co.ufps.edu.dao.ContenidoDao;
import co.ufps.edu.dao.EnlaceDeInteresDao;
import co.ufps.edu.dao.GaleriaDao;
import co.ufps.edu.dao.LoginDao;
import co.ufps.edu.dao.LogoDao;
import co.ufps.edu.dao.NoticiaDao;
import co.ufps.edu.dao.NovedadDao;
import co.ufps.edu.dao.RedSocialDao;
import co.ufps.edu.dao.SubCategoriaDao;
import co.ufps.edu.dao.UsuarioDao;
import co.ufps.edu.dao.VisitaDao;
import co.ufps.edu.dto.Actividad;
import co.ufps.edu.dto.Contenido;
import co.ufps.edu.dto.Galeria;
import co.ufps.edu.dto.Login;
import co.ufps.edu.dto.Novedad;
import co.ufps.edu.dto.Visita;
import co.ufps.edu.util.FileUtil;
import co.ufps.edu.util.JwtUtil;

@Controller
public class AdminController {

  @Autowired
  private SessionManager sessionManager;
  

  private JwtUtil jwtUtil;
  private LoginDao loginDao;
  private CategoriaDao categoriaDao;
  private SubCategoriaDao subCategoriaDao;

  private ContactoDao contactoDao;
  private RedSocialDao redSocialDao;
  private ComponenteDao componenteDao;
  private EnlaceDeInteresDao enlaceDeInteresDao;
  private VisitaDao visitaDao;
  private UsuarioDao usuarioDao;
  private FileUtil fileUtil;

  @Autowired
  private ContenidoDao contenidoDao;
  @Autowired
  private NoticiaDao noticiaDao;
  @Autowired
  private ActividadDao actividadDao;
  @Autowired
  private NovedadDao novedadDao;
  @Autowired
  private LogoDao logoDao;
  @Autowired
  private GaleriaDao galeriaDao;

  public AdminController() {
    jwtUtil = new JwtUtil();
    loginDao = new LoginDao();
    categoriaDao = new CategoriaDao();
    subCategoriaDao = new SubCategoriaDao();
    enlaceDeInteresDao = new EnlaceDeInteresDao();
    contactoDao = new ContactoDao();
    redSocialDao = new RedSocialDao();
    componenteDao = new ComponenteDao();
    visitaDao = new VisitaDao();
    usuarioDao = new UsuarioDao();
    fileUtil = new FileUtil();
    //usuarioDao.setMailSender(javaMailService);
  }

  @GetMapping("/") // Base
  public String main(Model model, HttpServletRequest request) {

    guardarVisita(request);
    cargarModelo(model);
    return "index"; // Nombre del archivo jsp
  }



  private void guardarVisita(HttpServletRequest request) {
    Visita visita = new Visita();
    visita.setIp(getClientIpAddr(request));
    visitaDao.registrarVisita(visita);
  }

  private void cargarModelo(Model model) {

    model.addAttribute("categorias", categoriaDao.getCategoriasConSubcategorias());
    model.addAttribute("noticias", noticiaDao.getUltimasNoticias());
    model.addAttribute("novedades", novedadDao.getUltimasNovedades());
    model.addAttribute("actividades", actividadDao.getUltimasActividades());
    model.addAttribute("galerias", galeriaDao.getGalerias());

    model.addAttribute("redes", redSocialDao.getRedesSociales());
    model.addAttribute("enlaces", enlaceDeInteresDao.getEnlacesDeInteres());
    model.addAttribute("contactos", contactoDao.getContactos());
    model.addAttribute("logoHorizontal", logoDao.getLogo("LogoHorizontal"));
    model.addAttribute("logoVertical", logoDao.getLogo("LogoVertical"));
    model.addAttribute("dependencia", fileUtil.getProperties().getProperty("name"));
    model.addAttribute("visitasDia", visitaDao.getCantidadVisitasDia());
    model.addAttribute("visitasMes", visitaDao.getCantidadVisitasMes());
    model.addAttribute("visitasSiempre", visitaDao.getCantidadVisitas());

  }

  @GetMapping("/admin") // Base
  public String index() {
    return "Administrador/Login"; // Nombre del archivo jsp
  }

  @GetMapping("/indexAdmin") // Base
  public String indexAdmin(Model model) {
    this.getCantidadRegistros(model);
    return "Administrador/indexAdmin"; // Nombre del archivo jsp
  }

  /**
   * Método solicitado por los formularios de los archivos .jsp
   * <p>
   * Este metodo es usado en la etiqueta form de la siguiente manera: modelAttribute="login"
   * 
   * @return
   */
  @ModelAttribute("login")
  public Login setUpUserForm() {
    return new Login();
  }

  /**
   * Método para autenticar al usuario al usuario.
   * 
   * @param login Objeto con los datos de autenticacion
   * @param model Clase para enviar datos desde los servicios a los archivos .jsp
   * @param request Objeto con los datos de sesion que por el instante es nulo.
   * @return La pagina a donde fue redireccionado.
   */
  @PostMapping("/autenticar")
  public String authenticateUser(@ModelAttribute("login") Login login, Model model,
      HttpServletRequest request) {

    /*
     * Consulto si los datos no vienen nulos
     */
    if (!StringUtils.isEmpty(login.getCorreoInstitucional())
        && !StringUtils.isEmpty(login.getContrasena())) {
      // Consulto en base de datos si se encuentra ese correo y esa contraseña
      String resultado =
          loginDao.authenticate(login.getCorreoInstitucional(), login.getContrasena());

      // Si el resultado no es vacio es por que si existe ese correo y esa contraseña
      if (!resultado.isEmpty()) {

        // Creo un Json Web Token para validar si la sesión esta activa
        String jwt = jwtUtil.generateToken(resultado, login.getCorreoInstitucional());

        // Guardo el JWT como atributo de sesión
        request.getSession().setAttribute("token", jwt);

        // Guarda la sesion en el manejador de sesiones
        sessionManager.guardarSession("SESSION:" + login.getCorreoInstitucional(), jwt);

        this.getCantidadRegistros(model);

        // Redirijo al index debido a que el usuario ya fue autenticado con exito
        return "Administrador/indexAdmin";

      } else {

        /**
         * Guardo en una variable el mensaje de error indicando que el usuario o la contraseña
         * fueron incorrectos debido a que no se encuentran en la base de datos y asi pueda ser
         * entendida por los archivos .JSP
         */
        model.addAttribute("wrong", "Usuario o contraseña incorrectos.");
      }
      // Redirecciono al login debido a que la autenticación fue incorrecta
      return "Administrador/Login";
    } else {
      /**
       * Guardo en una variable el mensaje de error indicando que el usuario o la contraseña son
       * nulos siendo estos datos son obligatorios, y asi pueda ser entendida por los archivos .JSP
       */
      model.addAttribute("wrong", "El usuario y la contraseña no pueden ser nulos.");
      // Redirecciono al login debido a que la autenticación fue incorrecta
      return "Administrador/Login";
    }
  }

  @GetMapping("/logout")
  private String getLogOut(String token, HttpServletRequest request) {
    request.getSession().invalidate();
    String correo = jwtUtil.parseToken(token);
    sessionManager.eliminarSesion("SESSION:" + correo);
    return "Administrador/Login"; // Nombre del archivo jsp
  }

  private void getCantidadRegistros(Model model) {
    model.addAttribute("catidadCategorias", this.categoriaDao.getCantidadCategorias());
    model.addAttribute("catidadSubCategorias", this.subCategoriaDao.getCantidadSubCategorias());
    model.addAttribute("catidadContenidos", this.contenidoDao.getCantidadContenidos());
    model.addAttribute("catidadNoticias", this.noticiaDao.getCantidadNoticias());
    model.addAttribute("catidadActividades", this.actividadDao.getCantidadActividades());
    model.addAttribute("catidadNovedades", this.novedadDao.getCantidadNovedades());
    model.addAttribute("catidadLogos", this.logoDao.getCantidadLogos());
    model.addAttribute("catidadEnlaces", this.enlaceDeInteresDao.getCantidadEnlacesDeInteres());
    model.addAttribute("catidadGalerias", this.galeriaDao.getCantidadGalerias());
    model.addAttribute("catidadContactos", this.contactoDao.getCantidadContactos());
    model.addAttribute("catidadRedesSociales", this.redSocialDao.getCantidadRedesSociales());
  }

  /**
   * Método que obtiene la pagina de obtener un componente dado un ID.
   * 
   * @param idCategoria Identificador del componente
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información del contenido cargado.
   */
  @GetMapping(value = "/servicios/componente")
  public String obtenerContenido(@RequestParam("id") long idComponente,
      @RequestParam("componente") String tipo, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idComponente <= 0) {
      return "index";
    }
    Contenido contenido = componenteDao.obtenerContenidoComponentePorId(idComponente, tipo);


    cargarModelo(model);
    model.addAttribute("titulo", (contenido == null) ? "" : contenido.getNombre());
    model.addAttribute("codigo", (contenido == null) ? "" : contenido.getContenido());
    if (contenido.getId() != 0) {
      return "contenido"; // Nombre del archivo jsp
    } else {
      return "index";
    }
  }

  /**
   * Método que obtiene la pagina de una galeria dado un ID.
   * 
   * @param idGaleria Identificador de la galeria
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la galeria cargada.
   */
  @GetMapping(value = "/servicios/galeria")
  public String obtenerContenido(@RequestParam("id") long idGaleria, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idGaleria <= 0) {
      return "index";
    }
    Galeria galeria = galeriaDao.obtenerGaleriaPorId(idGaleria);

    cargarModelo(model);
    model.addAttribute("galeria", galeria);
    return "galeria"; // Nombre del archivo jsp
  }

  /**
   * Método que obtiene la pagina de todas las novedades.
   *
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de las novedades cargada.
   */
  @GetMapping(value = "/servicios/novedades")
  public String obtenerNovedades(Model model) {

    List<Novedad> novedades = novedadDao.getNovedades();
    cargarModelo(model);
    model.addAttribute("novedadesCom", novedades);
    return "novedades"; // Nombre del archivo jsp
  }

  /**
   * Método que obtiene la pagina de todas las galerias.
   *
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de las galerias cargada.
   */
  @GetMapping(value = "/servicios/galerias")
  public String obtenerGalerias(Model model) {

    List<Galeria> galerias = galeriaDao.getGalerias();
    cargarModelo(model);
    model.addAttribute("galeriasCom", galerias);
    return "galerias"; // Nombre del archivo jsp
  }

  /**
   * Método que obtiene la pagina de todas las galerias.
   *
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de las galerias cargada.
   */
  @GetMapping(value = "/servicios/actividades")
  public String obtenerActividades(Model model) {

    List<Actividad> galerias = actividadDao.getActividades();
    cargarModelo(model);
    model.addAttribute("actividadCom", galerias);
    return "actividades"; // Nombre del archivo jsp
  }
  @GetMapping("/generarInforme")
  private String generarInforme() {
    return "xlsView"; // Nombre del archivo jsp
  }

  public static String getClientIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_X_FORWARDED");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_FORWARDED");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("HTTP_VIA");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getHeader("REMOTE_ADDR");
    }
    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  @GetMapping("/recordar") // Base
  public String recordar() {

    return "Administrador/Recordar"; // Nombre del archivo jsp
  }


  @PostMapping("/recordarContraseña")
  public String recordarContraseña(@ModelAttribute("login") Login login, Model model,
      HttpServletRequest request) {
    
    if(login.getCorreoInstitucional().equals("")) {
      model.addAttribute("wrong","Debes anotar por lo menos el correo.");
      return "Administrador/Recordar";
    }else {
      String mensaje = usuarioDao.enviarCorreo(login.getCorreoInstitucional());
      if(mensaje.equals("Actualizacion")) {
        model.addAttribute("result","Contraseña recuparada con éxito");
        return "Administrador/Login";
      }else {
        model.addAttribute("wrong",mensaje);
        return "Administrador/Recordar";
      }
    }

  }
}
