package co.ufps.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import co.ufps.edu.dao.CategoriaDao;
import co.ufps.edu.dto.Categoria;

/**
 * Controlador de categorias. Las categorias son las llamadas pestañas en el sitio web. Todos los
 * servicios publicados en esta clase seran expuestos para ser consumidos por los archivos .JSP
 * <p>
 * La etiqueta @Controller escanea todos los servicios para publicarlos segun el tipo de metodo
 * HTTP.
 * 
 * @author ufps
 *
 */
@Controller
public class CategoriaController {

  private CategoriaDao categoriaDao;

  /**
   * Constructor de la clase en donde se inicializan las variables
   */
  public CategoriaController() {
    categoriaDao = new CategoriaDao();
  }

  /**
   * Método que retorna una pagina con todas las categorias en el sistema.
   * 
   * @return La página principal de categorias.
   */
  @GetMapping("/categorias") // Base
  public String index(Model model) {
    // Cargamos las categorias para poder mostrarlas en el cuadro.
    model.addAttribute("categorias", categoriaDao.getCategorias());
    return "Administrador/Categoria/Categorias"; // Nombre del archivo jsp
  }

  /**
   * Modelo con el que se realizara el formulario
   * @return Un objeto para ser llenado desde el archivo .JSP
   */
  @ModelAttribute("categoria")
  public Categoria setUpUserForm() {
    return new Categoria();
  }

  /**
   * Método que retorna una pagina para realizar el registro de una categoria.
   * 
   * @return La página de registro de categorias.
   */
  @GetMapping("/registrarCategoria") // Base
  public String registrarCategoria() {
    return "Administrador/Categoria/RegistrarCategoria"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite guardar una categoria
   * 
   * @param categoria Objeto con la información a guardar
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/guardarCategoria")
  public String registrarCategoria(@ModelAttribute("categoria") Categoria categoria, Model model) {

    // Consulta si tiene todos los campos llenos
    if (categoria.isValidoParaRegistrar()) {
      String mensaje = categoriaDao.registrarCategoria(categoria);
      if (mensaje.equals("Registro exitoso")) {
        model.addAttribute("result", "Categoria registrada con éxito.");
        model.addAttribute("categorias", categoriaDao.getCategorias());
        return "Administrador/Categoria/Categorias"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Categoria/RegistrarCategoria";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Categoria/RegistrarCategoria";
    }
  }

  /**
   * Servicio que permite bajar el numero de orden de una categoria
   * 
   * @param idCategoria Idenrificador de la categoria
   * @param orden Numero de orden actual
   * @param model Donde se cargaran las categorias actualizadas
   * @return El redireccionamiento a la pagina de categorias
   */
  @GetMapping(value = "/bajarOrdenCategoria")
  public String bajarOrdenDeCategoria(@RequestParam("id") long idCategoria,
      @RequestParam("orden") int orden, Model model) {
    // Cambiar orden
    bajarOrden(idCategoria, orden);

    // Cargar categorias en model
    model.addAttribute("categorias", categoriaDao.getCategorias());

    return "Administrador/Categoria/Categorias";
  }

  /**
   * Metodo que baja de orden una categoria
   * 
   * @param idCategoria Identificador de la categoria
   * @param orden Numero de orden
   */
  private void bajarOrden(long idCategoria, int orden) {

    // Consulto que el Id sea mayor a 0.
    if (idCategoria <= 0) {
      return;
    }

    // Obtengo el menor número de ordenamiento
    int ordenMaximo = categoriaDao.getUltimoNumeroDeOrden();

    // Si el numero de orden es el máximo es por que ya es el ultimo y no se debe hacer nada.
    if (orden == ordenMaximo) {
      return;
      // Cambio el orden
    } else {
      categoriaDao.descenderOrden(idCategoria, orden);
    }
  }

  /**
   * Servicio que permite subir el numero de orden de una categoria
   * 
   * @param idCategoria Idenrificador de la categoria
   * @param orden Numero de orden actual
   * @param model Donde se cargaran las categorias actualizadas
   * @return El redireccionamiento a la pagina de categorias
   */
  @GetMapping(value = "/subirOrdenCategoria")
  public String subirOrdenDeCategoria(@RequestParam("id") long idCategoria,
      @RequestParam("orden") int orden, Model model) {
    // Cambiar orden
    subirOrden(idCategoria, orden);

    // Cargar categorias en model para ser leidas por los archivos .JSP
    model.addAttribute("categorias", categoriaDao.getCategorias());

    return "Administrador/Categoria/Categorias";
  }

  /**
   * Metodo que permite subir una categoria de orden
   * 
   * @param idCategoria Identificador de la categoria.
   * @param orden Orden de la categoria.
   */
  private void subirOrden(long idCategoria, int orden) {

    // Consulto que el Id sea mayor a 0.
    if (idCategoria <= 0) {
      return;
    }

    // Si el numero de orden es el minimo es por que ya es el primero y no se debe hacer nada.
    if (orden == 1) {
      return;
      // Cambio el orden
    } else {
      categoriaDao.ascenderOrden(idCategoria, orden);
    }
  }

  /**
   * Método que obtiene la pagina de actualizar categoria dado un ID.
   * 
   * @param idCategoria Identificador de la categoria
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la categoria cargada.
   */
  @GetMapping(value = "/actualizarCategoria")
  public String actualizarCategoria(@RequestParam("id") long idCategoria, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idCategoria <= 0) {
      model.addAttribute("categorias", categoriaDao.getCategorias());
      return "Administrador/Categoria/Categorias"; // Nombre del archivo jsp
    }
    Categoria categoria = categoriaDao.obtenerCategoriaPorId(idCategoria);
    model.addAttribute("categoria", categoria);
    return "Administrador/Categoria/ActualizarCategoria"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite editar una categoria.
   * 
   * @param categoria Objeto con la información a editar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/editarCategoria")
  public String editarCategoria(@ModelAttribute("categoria") Categoria categoria, Model model) {

    // Consulta si tiene todos los campos llenos
    if (categoria.isValidoParaRegistrar()) {
      String mensaje = categoriaDao.editarCategoria(categoria);
      if (mensaje.equals("Actualizacion exitosa")) {
        model.addAttribute("result", "Categoria actualizada con éxito.");
        model.addAttribute("categorias", categoriaDao.getCategorias());
        return "Administrador/Categoria/Categorias"; // Nombre del archivo jsp
      } else {
        model.addAttribute("wrong", mensaje);
        return "Administrador/Categoria/ActualizarCategoria";
      }
      //
    } else {
      model.addAttribute("wrong", "Debes llenar todos los campos.");
      return "Administrador/Categoria/ActualizarCategoria";
    }
  }

  /**
   * Método que obtiene la pagina de actualizar categoria dado un ID.
   * 
   * @param idCategoria Identificador de la categoria
   * @param model Objeto para enviar información a los archivos .JSP
   * @return La pagina con la información de la categoria cargada.
   */
  @GetMapping(value = "/eliminarCategoria")
  public String eliminarCategoria(@RequestParam("id") long idCategoria, Model model) {
    // Consulto que el Id sea mayor a 0.
    if (idCategoria <= 0) {
      model.addAttribute("categorias", categoriaDao.getCategorias());
      return "Administrador/Categoria/Categorias"; // Nombre del archivo jsp
    }
    Categoria categoria = categoriaDao.obtenerCategoriaPorId(idCategoria);
    model.addAttribute("categoria", categoria);
    return "Administrador/Categoria/EliminarCategoria"; // Nombre del archivo jsp
  }

  /**
   * Servicio que permite eliminar una categoria.
   * 
   * @param categoria Objeto con la información a eliminar.
   * @param model Modelo con la información necesaria para transportar a los archivos .JSP
   * @return La página a donde debe redireccionar después de la acción.
   */
  @PostMapping(value = "/borrarCategoria")
  public String borrarCategoria(@ModelAttribute("categoria") Categoria categoria, Model model) {

    String mensaje = categoriaDao.eliminarCategoria(categoria);
    cambiarOrdenDeCategorias(categoria);
    if (mensaje.equals("Eliminacion exitosa")) {
      model.addAttribute("result", "Categoria eliminada con éxito.");
      model.addAttribute("categorias", categoriaDao.getCategorias());
      return "Administrador/Categoria/Categorias"; // Nombre del archivo jsp
    } else {
      model.addAttribute("categoria", categoriaDao.obtenerCategoriaPorId(categoria.getId()));
      model.addAttribute("wrong", mensaje);
      return "Administrador/Categoria/EliminarCategoria";
    }

  }

  /**
   * Metodo que reordena todas las categorias dado un orden faltante.
   * @param categoria Objeto con la información de la categoria borrada.
   */
  private void cambiarOrdenDeCategorias(Categoria categoria) {
    // Obtengo el menor número de ordenamiento
    int ordenMaximo = categoriaDao.getUltimoNumeroDeOrden();
    
    for(int i = categoria.getOrden();i<ordenMaximo;i++) {
      long idCategoria = categoriaDao.getIdCategoriaPorOrden(i+1);
      categoriaDao.cambiarOrdenDeCategoria(idCategoria, i);
    }
  }
  
  
}
