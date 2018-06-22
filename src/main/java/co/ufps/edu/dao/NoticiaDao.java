package co.ufps.edu.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.Charsets;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.constantes.Constantes;
import co.ufps.edu.dto.Contenido;
import co.ufps.edu.dto.Noticia;
import co.ufps.edu.dto.TipoContenido;
import co.ufps.edu.util.ImagenUtil;

/**
 * Clase que permite acceder a la capa de datos en el entorno de noticias.
 * 
 * @author ufps
 *
 */
@Component
public class NoticiaDao {

  private SpringDbMgr springDbMgr;
  private ImagenUtil imagenUtil;

  public NoticiaDao() {
    springDbMgr = new SpringDbMgr();
    imagenUtil = new ImagenUtil();
  }

  /**
   * Método que obtiene la cantidad de noticias registradas.
   * 
   * @return Un entero con la cantidad de noticias creadas en el sistema.
   */
  public int getCantidadNoticias() {
    int cant = 0;
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM noticia ");

    if (sqlRowSet.next()) {
      cant = sqlRowSet.getInt("cantidad");
    }
    return cant;
  }

  /**
   * Metodo que consulta en base de datos todas las noticias existentes y las devuelve ordenadamente
   * 
   * @return Una lista con todas las noticias
   */
  public List<Noticia> getNoticias() {

    // Lista para retornar con los datos
    List<Noticia> noticias = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM noticia ORDER BY ORDEN ASC ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Noticia noticia = new Noticia();

      noticia.setId(sqlRowSet.getLong("id"));
      noticia.setNombre(sqlRowSet.getString("nombre"));
      noticia.setDescripcion(sqlRowSet.getString("descripcion"));
      noticia.setOrden(sqlRowSet.getInt("orden"));
      noticia.setFecha(sqlRowSet.getDate("fecha"));

      // Guarda el registro para ser retornado
      noticias.add(noticia);
    }

    // Retorna todas las noticias desde base de datos
    return noticias;
  }

  /**
   * Método que registra una noticia en base de datos
   * 
   * @param noticia Objeto con todos los datos de la noticia a registrar.
   * @return El resultado de la acción.
   */
  @Async
  public String registrarNoticia(Noticia noticia) {

    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("nombre", noticia.getNombre());
    map.addValue("descripcion", noticia.getDescripcion());
    map.addValue("orden", 1);
    map.addValue("fecha", noticia.getFecha());
    try {
      map.addValue("imagen1",
          new SqlLobValue(new ByteArrayInputStream(noticia.getImagen1().getBytes()),
              noticia.getImagen1().getBytes().length, new DefaultLobHandler()),
          Types.BLOB);
    } catch (IOException e) {
      new Exception();
      
    }

    map.addValue("orden", 1);

    // Armar la sentencia de actualización debase de datos
    String query =
        "INSERT INTO noticia(nombre,descripcion,orden,fecha,imagen1) VALUES(:nombre,:descripcion,:orden,:fecha,:imagen1)";

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
        : "Error al registrar la noticia. Contacte al administrador del sistema.";

  }

  public void cambiarOrden() {

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM noticia ORDER BY ORDEN DESC ");

    // Obtenemos el maximo orden
    int ultimoNumeroDeOrden = getUltimoNumeroDeOrden();

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      cambiarOrdenDeNoticia(sqlRowSet.getLong("id"), ultimoNumeroDeOrden + 1);
      ultimoNumeroDeOrden--;
    }

  }

  /**
   * Metodo que consulta en la base de datos cual es el ultimo de numero de ordenamiento que hay
   * entre todas las noticias
   * 
   * @return El último numero de orden de noticia.
   */
  public int getUltimoNumeroDeOrden() {

    // Consulta en base de datos el ultimo numero de ordenamiento
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM noticia ORDER BY ORDEN DESC ");

    // Si existe al menos una noticias retorna el numero
    if (sqlRowSet.next()) {
      return (sqlRowSet.getInt("orden"));
      // Si no existen noticias retorna el 0
    } else {
      return 0;
    }
  }


  /**
   * Metodo que actualiza el orden de una noticia.
   * 
   * @param id de la noticia.
   * @param orden para actualizar a la noticia.
   */
  public void cambiarOrdenDeNoticia(long id, int orden) {
    SpringDbMgr springDbMgr = new SpringDbMgr();

    // Agrego los datos del registro (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", id);
    map.addValue("orden", orden);

    // Armar la sentencia de actualización debase de datos
    String query = "UPDATE noticia SET orden = :orden WHERE id = :id";

    // Ejecutar la sentencia
    try {
      springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      new Exception();
    }
  }

  /**
   * Metodo que baja un nivel a la noticia en base de datos.
   * 
   * @param idNoticia Identificador de la noticia.
   * @param orden
   */
  public void descenderOrden(long idNoticia, int orden) {

    // Extraemos el id de la siguiente
    long idNoticiaSiguiente = getIdNoticiaPorOrden(orden + 1);

    // Modificamos el orden actual
    cambiarOrdenDeNoticia(idNoticia, -1);

    // Modificamos el orden de la siguiente noticia
    cambiarOrdenDeNoticia(idNoticiaSiguiente, orden);

    // Modificamos el orden de la noticia actual
    cambiarOrdenDeNoticia(idNoticia, orden + 1);
  }

  /**
   * Metodo que consulta en base de datos el ID de una noticia dado un numero de orden.
   * 
   * @param orden Numero de orden por el cual se filtrara la busqueda.
   * @return El ID de la noticia.
   */
  public long getIdNoticiaPorOrden(int orden) {
    // Consulta en base de datos el ultimo numero de ordenamiento
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("orden", orden);
    SqlRowSet sqlRowSet =
        springDbMgr.executeQuery(" SELECT * FROM noticia WHERE orden = :orden ", map);

    // Si existe al menos una noticia retorna el numero
    if (sqlRowSet.next()) {
      return (sqlRowSet.getLong("id"));
      // Si no existen noticias retorna el 0
    } else {
      return 0l;
    }
  }

  /**
   * Método que permite subir de orden una noticia en base de datos.
   * 
   * @param idNoticia Identificador de la noticia
   * @param orden Numero de orden
   */
  public void ascenderOrden(long idNoticia, int orden) {
    // Extraemos el id de la noticia anterior
    long idNoticiaAnterior = getIdNoticiaPorOrden(orden - 1);

    // Modificamos el orden actual
    cambiarOrdenDeNoticia(idNoticia, -1);

    // Modificamos el orden de la anterior noticia
    cambiarOrdenDeNoticia(idNoticiaAnterior, orden);

    // Modificamos el orden de la noticia actual
    cambiarOrdenDeNoticia(idNoticia, orden - 1);

  }

  /**
   * Metodo que consulta en base de datos la informacion de una noticia dada
   * 
   * @param idNoticia Identificador de la noticia.
   * @return La informacion de una noticia en un objeto.
   */
  public Noticia obtenerNoticiaPorId(long idNoticia) {
    // Lista para retornar con los datos
    Noticia noticia = new Noticia();

    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", idNoticia);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM noticia WHERE id = :id", map);

    // Consulto si la noticia existe
    if (sqlRowSet.next()) {
      // Almaceno los datos de la noticia
      noticia.setId(sqlRowSet.getLong("id"));
      noticia.setNombre(sqlRowSet.getString("nombre"));
      noticia.setDescripcion(sqlRowSet.getString("descripcion"));
      noticia.setOrden(sqlRowSet.getInt("orden"));
      noticia.setFecha(sqlRowSet.getDate("fecha"));

      Object imagen1Blob = sqlRowSet.getObject("imagen1");
      noticia.setIm1Base64image(imagenUtil.convertirImagen((byte[]) imagen1Blob));

    }

    // Retorna la noticia desde base de datos
    return noticia;
  }

  @Async
  public String editarNoticia(Noticia noticia) {

    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", noticia.getId());
    map.addValue("nombre", noticia.getNombre());
    map.addValue("descripcion", noticia.getDescripcion());
    map.addValue("fecha", noticia.getFecha());
    String sqlImagen1 = "";
    if (noticia.getImagen1().getSize() > 0) {
      try {
        map.addValue("imagen1",
            new SqlLobValue(new ByteArrayInputStream(noticia.getImagen1().getBytes()),
                noticia.getImagen1().getBytes().length, new DefaultLobHandler()),
            Types.BLOB);
        sqlImagen1 = ", imagen1 = :imagen1";
      } catch (IOException e) {
        // TODO Auto-generated catch block
        new Exception();
      }
    }


    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE noticia SET nombre = :nombre, descripcion = :descripcion, fecha = :fecha "
            + sqlImagen1 + " WHERE id = :id";

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
        : "Error al actualizar la noticia. Contacte al administrador del sistema.";
  }


  public String eliminarNoticia(Noticia noticia) {

    // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", noticia.getId());

    // Armar la sentencia de actualización debase de datos
    String query = "DELETE FROM noticia WHERE id = :id";

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
        : "Error al eliminar la noticia. Contacte al administrador del sistema.";
  }

  public List<Noticia> getUltimasNoticias() {
    // Lista para retornar con los datos
    List<Noticia> noticias = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM noticia ORDER BY ORDEN ASC LIMIT 0, 4");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Noticia noticia = new Noticia();

      noticia.setId(sqlRowSet.getLong("id"));
      noticia.setNombre(sqlRowSet.getString("nombre"));
      noticia.setDescripcion(sqlRowSet.getString("descripcion"));
      noticia.setOrden(sqlRowSet.getInt("orden"));
      noticia.setFecha(sqlRowSet.getDate("fecha"));

      Object imagen1Blob = sqlRowSet.getObject("imagen1");
      noticia.setIm1Base64image(imagenUtil.convertirImagen((byte[]) imagen1Blob));


      cargarContenido(noticia);
      
      // Guarda el registro para ser retornado
      noticias.add(noticia);
    }

    // Retorna todas las noticias desde base de datos
    return noticias;
  }

  private void cargarContenido(Noticia noticia) {

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", noticia.getId());
    map.addValue("tipo", Constantes.NOTICIA);
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery( " SELECT    contenido.id                    idContenido,            "
                                                  + "           contenido.contenido             contenido,              "
                                                  + "           contenido.TipoContenido_id      tipoContenido          "                                                  
                                                  + "   FROM    noticia                                            "
                                                  + "INNER JOIN contenido  ON contenido.asociacion = noticia.id "
                                                  + "WHERE noticia.id = :id "
                                                  + "AND   contenido.tipoasociacion = :tipo",map);

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro

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
      noticia.setContenido(contenido);
    }
  }

}
