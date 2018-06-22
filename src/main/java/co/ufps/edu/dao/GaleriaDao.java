package co.ufps.edu.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.dto.Galeria;
import co.ufps.edu.dto.Imagen;
import co.ufps.edu.dto.ResultDB;

/**
 * Clase que permite acceder a la capa de datos en el entorno de galerias.
 * 
 * @author ufps
 *
 */
@Component
public class GaleriaDao {

  private SpringDbMgr springDbMgr;
  
  public GaleriaDao() {
    springDbMgr = new SpringDbMgr();
  }

  /**
   * Metodo que consulta en base de datos todas las galerias existentes.
   * 
   * @return Una lista con todas las novedades
   */
  public List<Galeria> getGalerias() {

    // Lista para retornar con los datos
    List<Galeria> galerias = new LinkedList<>();
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM galeria ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Galeria galeria = new Galeria();

      galeria.setId(sqlRowSet.getLong("id"));
      galeria.setNombre(sqlRowSet.getString("nombre"));
      galeria.setDescripcion(sqlRowSet.getString("descripcion"));
      galeria.setFecha(sqlRowSet.getDate("fecha"));
      guardarPrimeraImagen(galeria);
      
      // Guarda el registro para ser retornado
      galerias.add(galeria);
    }
    // Retorna todos las galerias desde base de datos
    return galerias;
  }

  private void guardarPrimeraImagen(Galeria galeria) {
    // Lista para retornar con los datos
    
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", galeria.getId());
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM foto WHERE Galeria_id = :id LIMIT 0,1 ",map);

    // Recorre cada registro obtenido de base de datos
    if (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Object imagenBlob = sqlRowSet.getObject("contenido");
      galeria.setPrimeraImagen(new String( (byte[]) imagenBlob));      
    }


  }

  /**
   * Método que registra una galeria en base de datos
   * 
   * @param galeria Objeto con todos los datos de la galeria a registrar.
   * @return El resultado de la acción.
   */
  @Async
  public String registrarGaleria(Galeria galeria) {
    
    // Agrego los datos del registro (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("nombre", galeria.getNombre());
    map.addValue("descripcion", galeria.getDescripcion());
    map.addValue("fecha", galeria.getFecha());

    // Armar la sentencia de actualización de base de datos
    String query =
        "INSERT INTO galeria(nombre, descripcion, fecha) VALUES(:nombre, :descripcion, :fecha)";

    // Ejecutar la sentencia
    ResultDB result = null;
    try {
      result = springDbMgr.executeDmlWithKey(query, map);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Se guardan las imagenes asociadas a la galeria
    guardarImagenes(result.getKey(), galeria.getImagenes());

    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result.getKey() > 0) ? "Registro exitoso"
        : "La información de la galeria ya se encuentra en el sistema.";
  }


  private void guardarImagenes(long key, ArrayList<Imagen> imagenes) {
    for(Imagen imagen:imagenes) {
      // Agrego los datos del registro (nombreColumna/Valor)
      MapSqlParameterSource map = new MapSqlParameterSource();
      map.addValue("Galeria_id", key);
      map.addValue("descripcion", imagen.getDescripcion());
      map.addValue("contenido", imagen.getImagen());

      // Armar la sentencia de actualización de base de datos
      String query =
          "INSERT INTO foto(Galeria_id, descripcion, contenido) VALUES(:Galeria_id, :descripcion, :contenido)";

      // Ejecutar la sentencia
      int result = 0;
      try {
        result = springDbMgr.executeDml(query, map);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

  }

  /**
   * Método que consulta en base de datos la informacion de una galeria
   * 
   * @param idGaleria Identificador de galeria.
   * @return La informacion de una galeria en un objeto.
   */
  public Galeria obtenerGaleriaPorId(long idGaleria) {
    // Lista para retornar con los datos
    Galeria galeria = new Galeria();

    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", idGaleria);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM galeria WHERE id = :id", map);

    // Consulto si la galeria existe
    if (sqlRowSet.next()) {
      // Almaceno los datos de contacto
      galeria.setId(sqlRowSet.getLong("id"));
      galeria.setNombre(sqlRowSet.getString("nombre"));
      galeria.setDescripcion(sqlRowSet.getString("descripcion"));
      galeria.setFecha(sqlRowSet.getDate("fecha"));
    }
    
    List<Imagen> imagenes = getImagenesPorIDCompletas(galeria.getId());
    
    galeria.setImagenes(new ArrayList<>(imagenes));
    
    // Retorna galeria desde base de datos
    return galeria;
  }


  /**
   * Método que consulta en base de datos la informacion de una galeria
   * 
   * @param idGaleria Identificador de galeria.
   * @return La informacion de una galeria en un objeto.
   */
  @Async
  public String editarGaleria(Galeria galeria) {

    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", galeria.getId());
    map.addValue("nombre", galeria.getNombre());
    map.addValue("descripcion", galeria.getDescripcion());
    map.addValue("fecha", galeria.getFecha());

    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE galeria SET nombre = :nombre, descripcion = :descripcion, fecha = :fecha  WHERE id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      e.printStackTrace();
    }
    borrarImagenes(galeria.getId());
    guardarImagenes(galeria.getId(),galeria.getImagenes());
    
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Actualizacion exitosa" : "La galeria ya se encuentra en el sistema.";
  }


  private void borrarImagenes(long id) {
 // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", id);

    // Armar la sentencia de eliminación debase de datos
    String query = "DELETE FROM foto WHERE Galeria_id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public String eliminarGaleria(Galeria galeria) {

    borrarImagenes(galeria.getId());
    
    // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", galeria.getId());

    // Armar la sentencia de eliminación debase de datos
    String query = "DELETE FROM galeria WHERE id = :id";

    // Ejecutar la sentencia
    int result = 0;
    try {
      result = springDbMgr.executeDml(query, map);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Si hubieron filas afectadas es por que si hubo registro, en caso contrario muestra el mensaje
    // de error.
    return (result == 1) ? "Eliminacion exitosa" : "No fue posible eliminar la galeria";
  }

  /*
   * Método que obtiene la cantidad de galerias registradas
   */
  public int getCantidadGalerias() {
    int cant = 0;
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM galeria ");

    if (sqlRowSet.next()) {
      cant = sqlRowSet.getInt("cantidad");
    }
    return cant;
  }

  public List<Imagen> getImagenesPorIDCompletas(long id) {

    // Lista para retornar con los datos
    List<Imagen> imagenes = new LinkedList<>();
    
    // Carga de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id",id );
    
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM foto WHERE Galeria_id = :id",map);

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Imagen imagen = new Imagen();

      imagen.setDescripcion(sqlRowSet.getString("descripcion"));
      Object imagenBlob = sqlRowSet.getObject("contenido");
      imagen.setImagen(new String( (byte[]) imagenBlob));      
      
      // Guarda el registro para ser retornado
      imagenes.add(imagen);
    }
    // Retorna todos las galerias desde base de datos
    return imagenes;

  }
}
