package co.ufps.edu.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.Charsets;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.constantes.Constantes;
import co.ufps.edu.dto.Categoria;
import co.ufps.edu.dto.Contenido;
import co.ufps.edu.dto.SubCategoria;
import co.ufps.edu.dto.TipoContenido;

/**
 * Clase que permite acceder a la capa de datos en el entorno de categorias.
 * @author ufps
 *
 */
public class CategoriaDao {

  private SpringDbMgr springDbMgr;

  public CategoriaDao() {
    springDbMgr = new SpringDbMgr();
  }
  
  /**
   * Metodo que consulta en base de datos todas las categorias existentes y las devuelve
   * ordenadamente
   * 
   * @return Una lista con todas las categorias
   */
  public List<Categoria> getCategorias() {

    // Lista para retornar con los datos
    List<Categoria> categorias = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM categoria ORDER BY ORDEN ASC ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Categoria categoria = new Categoria();

      categoria.setId(sqlRowSet.getLong("id"));
      categoria.setNombre(sqlRowSet.getString("nombre"));
      categoria.setDescripcion(sqlRowSet.getString("descripcion"));
      categoria.setOrden(sqlRowSet.getInt("orden"));

      // Guarda el registro para ser retornado
      categorias.add(categoria);
    }

    // Retorna todos las categorias desde base de datos
    return categorias;
  }

  /**
   * Método que registra una categoria en base de datos
   * 
   * @param categoria Objeto con todos los datos de la categoria a registrar.
   * @return El resultado de la acción.
   */
  public String registrarCategoria(Categoria categoria) {

    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("nombre", categoria.getNombre());
    
    map.addValue("descripcion", categoria.getDescripcion());
    map.addValue("orden", getUltimoNumeroDeOrden() + 1);

    // Armar la sentencia de actualización debase de datos
    String query =
        "INSERT INTO categoria(nombre,descripcion,orden) VALUES(:nombre,:descripcion,:orden)";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Registro exitoso"
        : "El nombre de la categoria ya se encuentra en el sistema.";

  }

  /**
   * Metodo que consulta en la base de datos cual es el ultimo de numero de ordenamiento que hay
   * entre todas las categorias
   * 
   * @return El último numero de orden de categoria.
   */
  public int getUltimoNumeroDeOrden() {

    // Consulta en base de datos el ultimo numero de ordenamiento
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM categoria ORDER BY ORDEN DESC ");

    // Si existe al menos una categoria retorna el numero
    if (sqlRowSet.next()) {
      return (sqlRowSet.getInt("orden"));
      // Si no existen categorias retorna el 0
    } else {
      return 0;
    }
  }

  /**
   * Metodo que baja un nivel a la categoria en base de datos.
   * 
   * @param idCategoria Identificador de la categoria.
   * @param orden
   */
  public void descenderOrden(long idCategoria, int orden) {

    // Extraemos el id de la siguiente
    long idCategoriaSiguiente = getIdCategoriaPorOrden(orden + 1);

    // Modificamos el orden actual
    cambiarOrdenDeCategoria(idCategoria, -1);

    // Modificamos el orden de la siguiente categoria
    cambiarOrdenDeCategoria(idCategoriaSiguiente, orden);

    // Modificamos el orden de la categoria actual
    cambiarOrdenDeCategoria(idCategoria, orden + 1);
  }

  /**
   * Metodo que consulta en base de datos el ID de una categoria dado un numero de orden.
   * 
   * @param orden Numero de orden por el cual se filtrara la busqueda.
   * @return El ID de la categoria.
   */
  public long getIdCategoriaPorOrden(int orden) {
    // Consulta en base de datos el ultimo numero de ordenamiento
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("orden", orden);
    SqlRowSet sqlRowSet =
        springDbMgr.executeQuery(" SELECT * FROM categoria WHERE orden = :orden ", map);

    // Si existe al menos una categoria retorna el numero
    if (sqlRowSet.next()) {
      return (sqlRowSet.getLong("id"));
      // Si no existen categorias retorna el 0
    } else {
      return 0l;
    }
  }

  /**
   * Metodo que actualiza el orden de una categoria.
   * 
   * @param id de la categoria.
   * @param orden para actualizar a la categoria.
   */
  public void cambiarOrdenDeCategoria(long id, int orden) {
    SpringDbMgr springDbMgr = new SpringDbMgr();

    // Agrego los datos del registro (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", id);
    map.addValue("orden", orden);

    // Armar la sentencia de actualización debase de datos
    String query = "UPDATE categoria SET orden = :orden WHERE id = :id";

    // Ejecutar la sentencia
    try {
      springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
  }

  /**
   * Método que permite subir de orden una categoria en base de datos.
   * @param idCategoria Identificador de la categoria
   * @param orden Numero de orden
   */
  public void ascenderOrden(long idCategoria, int orden) {
    // Extraemos el id de la categoria anterior
    long idCategoriaAnterior = getIdCategoriaPorOrden(orden - 1);

    // Modificamos el orden actual
    cambiarOrdenDeCategoria(idCategoria, -1);

    // Modificamos el orden de la anterior categoria
    cambiarOrdenDeCategoria(idCategoriaAnterior, orden);

    // Modificamos el orden de la categoria actual
    cambiarOrdenDeCategoria(idCategoria, orden - 1);

  }

  /**
   * Metodo que consulta en base de datos la informacion de una categoria dada
   * 
   * @param idCategoria Identificador de la categoria.
   * @return La informacion de una categoria en un objeto.
   */
  public Categoria obtenerCategoriaPorId(long idCategoria) {
    // Lista para retornar con los datos
    Categoria categoria = new Categoria();

    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", idCategoria);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM categoria WHERE id = :id", map);

    // Consulto si la categoria existe
    if (sqlRowSet.next()) {
      // Almaceno los datos de la categoria
      categoria.setId(sqlRowSet.getLong("id"));
      categoria.setNombre(sqlRowSet.getString("nombre"));
      categoria.setDescripcion(sqlRowSet.getString("descripcion"));
      categoria.setOrden(sqlRowSet.getInt("orden"));
    }

    // Retorna la categoria desde base de datos
    return categoria;
  }

  public String editarCategoria(Categoria categoria) {
    

    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", categoria.getId());
    map.addValue("nombre", categoria.getNombre());
    map.addValue("descripcion", categoria.getDescripcion());

    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE categoria SET nombre = :nombre, descripcion = :descripcion WHERE id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Actualizacion exitosa"
        : "El nombre de la categoria ya se encuentra en el sistema.";
  }

  public String eliminarCategoria(Categoria categoria) {
    

    // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", categoria.getId());

    // Armar la sentencia de actualización debase de datos
    String query = "DELETE FROM categoria WHERE id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Eliminacion exitosa"
        : "La categoria tiene contenido asociado. Debe eliminar el contenido y las subcategorias asociadas a esta categoria para poder realizar el eliminado.";
  }

  public Map<Long,String> getMapaDeCategorias() {
 // Lista para retornar con los datos
    Map<Long,String> categorias = new HashMap<Long, String>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM categoria ORDER BY ORDEN ASC ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      categorias.put(sqlRowSet.getLong("id"), sqlRowSet.getString("nombre"));
    }

    // Retorna todos las categorias desde base de datos
    return categorias;
  }
  
  /*
   * Método que obtiene la cantidad de categorias registradas
   */
  public int getCantidadCategorias() {

	  	int cant = 0;
	    // Consulta para realizar en base de datos
	    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM categoria "); 
	    
	    if (sqlRowSet.next()) {
	    	cant = sqlRowSet.getInt("cantidad");
	    }
	    return cant;
  }

  public List<Categoria> getCategoriasConSubcategorias() {
    // Lista para retornar con los datos
    List<Categoria> categorias = new LinkedList<>();

    categorias = getCategorias();
    
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery( " SELECT    categoria.id                    idCategoria,            "
                                                  + "           categoria.nombre                nombreCategoria,        "
                                                  + "           categoria.descripcion           descripcionCategoria,   "
                                                  + "           categoria.orden                 ordenCategoria,         "
                                                  + "           subcategoria.id                 idSubcategoria,         "
                                                  + "           subcategoria.nombre             nombreSubCategoria,     "
                                                  + "           subcategoria.descripcion        descripcionSubCategoria,"
                                                  + "           subcategoria.orden              ordenSubCategoria       "
                                                  + "   FROM    subcategoria                                            "
                                                  + "INNER JOIN categoria  ON categoria.id = subcategoria.Categoria_id  "
                                                  + "ORDER BY   categoria.orden ASC,subcategoria.orden ASC              ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      SubCategoria subCategoria = new SubCategoria();

      subCategoria.setId(sqlRowSet.getLong("idSubcategoria"));
      subCategoria.setNombre(sqlRowSet.getString("nombreSubCategoria"));
      subCategoria.setDescripcion(sqlRowSet.getString("descripcionSubCategoria"));
      subCategoria.setOrden(sqlRowSet.getInt("ordenSubCategoria"));
      cargarContenido(subCategoria);
      
      Categoria categoria = getCategoria(categorias,sqlRowSet.getLong("idCategoria"),sqlRowSet);
      
      // Guarda el registro para ser retornado
      categoria.agregarSubcategoria(subCategoria);
    }

    // Retorna todos las categorias desde base de datos
    return categorias;
  }

  private void cargarContenido(SubCategoria subCategoria) {
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", subCategoria.getId());
    map.addValue("tipo", Constantes.SUBCATEGORIA);
 // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery( " SELECT    contenido.id                    idContenido,            "
                                                  + "           contenido.contenido             contenido,              "
                                                  + "           contenido.TipoContenido_id      tipoContenido,          "                                                  
                                                  + "           subcategoria.id                 idSubcategoria,         "
                                                  + "           subcategoria.nombre             nombreSubCategoria,     "
                                                  + "           subcategoria.descripcion        descripcionSubCategoria,"
                                                  + "           subcategoria.orden              ordenSubCategoria       "
                                                  + "   FROM    subcategoria                                            "
                                                  + "INNER JOIN contenido  ON contenido.asociacion = subcategoria.id "
                                                  + "WHERE subcategoria.id = :id "
                                                  + "AND   contenido.tipoasociacion = :tipo",map);

    // Recorre cada registro obtenido de base de datos
    if (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Contenido contenido = new Contenido();
      
      contenido.setId(sqlRowSet.getLong("idContenido"));      
      byte []a = (byte[]) sqlRowSet.getObject("contenido");
      String res = new String(a,Charsets.UTF_8);
      contenido.setContenido(res);      

      TipoContenido tipoContenido = new TipoContenido();
      tipoContenido.setId(sqlRowSet.getLong("tipoContenido"));
            
      contenido.setTipoContenido(tipoContenido);
      
      // Guarda el registro para ser retornado
      subCategoria.setContenido(contenido);
      
      
      
    }

    
  }

  private Categoria getCategoria(List<Categoria> categorias, long idCategoria, SqlRowSet sqlRowSet) {
    
    for(Categoria cat:categorias) {
      if(cat.getId() == idCategoria) {
        return cat;
      }
    }
    
    Categoria categoria = new Categoria();
    categoria.setId(sqlRowSet.getLong("idCategoria"));
    categoria.setNombre(sqlRowSet.getString("nombreCategoria"));
    categoria.setDescripcion(sqlRowSet.getString("descripcionCategoria"));
    categoria.setOrden(sqlRowSet.getInt("ordenCategoria"));
    categorias.add(categoria);
    return categoria;
    
  }
  
  public static void main(String[] args) {
    CategoriaDao cate = new CategoriaDao();
    cate.getCategorias();
  }

}
