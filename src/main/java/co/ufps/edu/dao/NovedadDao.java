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
import co.ufps.edu.dto.Novedad;
import co.ufps.edu.dto.TipoContenido;
import co.ufps.edu.util.ImagenUtil;

@Component
public class NovedadDao {

  private SpringDbMgr springDbMgr;
  private ImagenUtil imagenUtil;

  public NovedadDao() {
    springDbMgr = new SpringDbMgr();
    imagenUtil = new ImagenUtil();
  }


  /**
   * Metodo que consulta en base de datos todas las novedades existentes.
   * 
   * @return Una lista con todas las novedades
   */
  public List<Novedad> getNovedades() {

    // Lista para retornar con los datos
    List<Novedad> novedades = new LinkedList<>();
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM novedad ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Novedad novedad = new Novedad();

      novedad.setId(sqlRowSet.getLong("id"));
      novedad.setNombre(sqlRowSet.getString("nombre"));
      novedad.setFecha(sqlRowSet.getDate("fecha"));
      Object imagen1Blob = sqlRowSet.getObject("imagen");
      novedad.setImBase64image(imagenUtil.convertirImagen((byte[]) imagen1Blob));
      // Guarda el registro para ser retornado
      novedades.add(novedad);
    }
    // Retorna todos los contactos desde base de datos
    return novedades;
  }

  /**
   * Método que registra una novedad en base de datos
   * 
   * @param novedad Objeto con todos los datos de la novedad a registrar.
   * @return El resultado de la acción.
   */
  @Async
  public String registrarNovedad(Novedad novedad) {

    // Agrego los datos del registro (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("nombre", novedad.getNombre());
    map.addValue("fecha", novedad.getFecha());
    try {
      map.addValue("imagen",
          new SqlLobValue(new ByteArrayInputStream(novedad.getImagen().getBytes()),
              novedad.getImagen().getBytes().length, new DefaultLobHandler()),
          Types.BLOB);
    } catch (IOException e) {
      new Exception();
    }

    // Armar la sentencia de actualización de base de datos
    String query = "INSERT INTO novedad(nombre, fecha, imagen) VALUES(:nombre, :fecha, :imagen)";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Registro exitoso"
        : "La información de la novedad ya se encuentra en el sistema.";
  }


  /**
   * Método que consulta en base de datos la informacion de una novedad
   * 
   * @param idNovedad Identificador de novedad.
   * @return La informacion de una novedad en un objeto.
   */
  public Novedad obtenerNovedadPorId(long idNovedad) {
    // Lista para retornar con los datos
    Novedad novedad = new Novedad();

    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", idNovedad);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM novedad WHERE id = :id", map);

    // Consulto si la novedad existe
    if (sqlRowSet.next()) {
      // Almaceno los datos de contacto
      novedad.setId(sqlRowSet.getLong("id"));
      novedad.setNombre(sqlRowSet.getString("nombre"));
      novedad.setFecha(sqlRowSet.getDate("fecha"));

      Object imagen1Blob = sqlRowSet.getObject("imagen");
      novedad.setImBase64image(imagenUtil.convertirImagen((byte[]) imagen1Blob));
    }

    // Retorna contacto desde base de datos
    return novedad;
  }


  /**
   * Método que consulta en base de datos la informacion de una novedad
   * 
   * @param idNovedad Identificador de novedad.
   * @return La informacion de una novedad en un objeto.
   */
  @Async
  public String editarNovedad(Novedad novedad) {

    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", novedad.getId());
    map.addValue("nombre", novedad.getNombre());
    map.addValue("fecha", novedad.getFecha());
    String sqlImagen = "";
    if (novedad.getImagen().getSize() > 0) {
      try {
        map.addValue("imagen",
            new SqlLobValue(new ByteArrayInputStream(novedad.getImagen().getBytes()),
                novedad.getImagen().getBytes().length, new DefaultLobHandler()),
            Types.BLOB);
        sqlImagen = ", imagen = :imagen";
      } catch (IOException e) {
        new Exception();
      }
    }

    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE novedad SET nombre = :nombre, fecha = :fecha " + sqlImagen + "  WHERE id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Actualizacion exitosa" : "La novedad ya se encuentra en el sistema.";
  }


  public String eliminarNovedad(Novedad novedad) {

    // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", novedad.getId());

    // Armar la sentencia de eliminación debase de datos
    String query = "DELETE FROM novedad WHERE id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Eliminacion exitosa" : "No fue posible eliminar la novedad";
  }


  /*
   * Método que obtiene la cantidad de novedades registradas
   */
  public int getCantidadNovedades() {
    int cant = 0;
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM novedad ");

    if (sqlRowSet.next()) {
      cant = sqlRowSet.getInt("cantidad");
    }
    return cant;
  }


  public List<Novedad> getUltimasNovedades() {
    // Lista para retornar con los datos
    List<Novedad> novedades = new LinkedList<>();
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet =
        springDbMgr.executeQuery(" SELECT * FROM novedad ORDER BY FECHA DESC LIMIT 0, 4 ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Novedad novedad = new Novedad();

      novedad.setId(sqlRowSet.getLong("id"));
      novedad.setNombre(sqlRowSet.getString("nombre"));
      novedad.setFecha(sqlRowSet.getDate("fecha"));
      Object imagen1Blob = sqlRowSet.getObject("imagen");
      novedad.setImBase64image(imagenUtil.convertirImagen((byte[]) imagen1Blob));

      cargarContenido(novedad);
      
      // Guarda el registro para ser retornado
      novedades.add(novedad);
    }
    // Retorna todos los contactos desde base de datos
    return novedades;
  }
  
  private void cargarContenido(Novedad novedad) {

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", novedad.getId());
    map.addValue("tipo", Constantes.NOVEDAD);
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery( " SELECT    contenido.id                    idContenido,            "
                                                  + "           contenido.contenido             contenido,              "
                                                  + "           contenido.TipoContenido_id      tipoContenido          "                                                  
                                                  + "   FROM    novedad                                            "
                                                  + "INNER JOIN contenido  ON contenido.asociacion = novedad.id "
                                                  + "WHERE novedad.id = :id "
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
      novedad.setContenido(contenido);
    }
  }  
}
