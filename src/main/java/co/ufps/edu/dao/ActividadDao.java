package co.ufps.edu.dao;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.Charsets;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.constantes.Constantes;
import co.ufps.edu.dto.Actividad;
import co.ufps.edu.dto.Contenido;
import co.ufps.edu.dto.TipoContenido;

/**
 * Clase que permite acceder a la capa de datos en el entorno de actividades.
 * @author ufps
 *
 */
@Component
public class ActividadDao {

  private SpringDbMgr springDbMgr;

  public ActividadDao() {
    springDbMgr = new SpringDbMgr();
  }

  /**
   * Método que obtiene el numero de actividades guardadas en base de datos.
   * @return La cantidad de actividades registradas.
   */
  public int getCantidadActividades() {
    int cant = 0;
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet =
        springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM proximaactividad ");

    if (sqlRowSet.next()) {
      cant = sqlRowSet.getInt("cantidad");
    }
    return cant;
  }

  /**
   * Metodo que consulta en base de datos todas las actividades existentes y las devuelve
   * ordenadamente
   * 
   * @return Una lista con todas las actividades
   */
  public List<Actividad> getActividades() {

    // Lista para retornar con los datos
    List<Actividad> actividades = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM proximaactividad ORDER BY fechaInicial DESC ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Actividad actividad = new Actividad();

      actividad.setId(sqlRowSet.getLong("id"));
      actividad.setNombre(sqlRowSet.getString("nombre"));
      actividad.setLugar(sqlRowSet.getString("lugar"));
      actividad.setFechaInicial(sqlRowSet.getDate("fechaInicial"));
      actividad.setFechaFinal(sqlRowSet.getDate("fechaFinal"));
      actividad.crearFechaFormato();

      // Guarda el registro para ser retornado
      actividades.add(actividad);
    }

    // Retorna todos las actividades desde base de datos
    return actividades;
  }

  public String registrarActividad(Actividad actividad) {
    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("nombre", actividad.getNombre());
    map.addValue("lugar", actividad.getLugar());
    map.addValue("fechaInicial", actividad.getFechaInicial());
    map.addValue("fechaFinal", actividad.getFechaFinal());

    // Armar la sentencia de actualización debase de datos
    String query =
        "INSERT INTO proximaactividad(nombre,lugar,fechaInicial,fechaFinal) VALUES(:nombre,:lugar,:fechaInicial,:fechaFinal)";

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
        : "Error al registrar la actividad. Contacte al administrador del sistema.";
  }  

  /**
   * Metodo que consulta en base de datos la informacion de una actividad dada
   * 
   * @param idActividad Identificador de la actividad.
   * @return La información de una actividad en un objeto.
   */
  public Actividad obtenerActividadPorId(long idActividad) {
    // Lista para retornar con los datos
    Actividad actividad = new Actividad();

    // Consulta para realizar en base de datos
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", idActividad);
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM proximaactividad WHERE id = :id", map);

    // Consulto si la actividad existe
    if (sqlRowSet.next()) {
      // Almaceno los datos de la actividad
      actividad.setId(sqlRowSet.getLong("id"));
      actividad.setNombre(sqlRowSet.getString("nombre"));
      actividad.setLugar(sqlRowSet.getString("lugar"));
      actividad.setFechaInicial(sqlRowSet.getDate("fechaInicial"));
      actividad.setFechaFinal(sqlRowSet.getDate("fechaFinal"));
      actividad.crearFechaFormato();
    }
    // Retorna la actividad desde base de datos
    return actividad;
  }
  

  public String editarActividad(Actividad actividad) {

    // Agrego los datos del registro (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", actividad.getId());
    map.addValue("nombre", actividad.getNombre());
    map.addValue("lugar", actividad.getLugar());
    map.addValue("fechaInicial", actividad.getFechaInicial());
    map.addValue("fechaFinal", actividad.getFechaFinal());


    // Armar la sentencia de actualización debase de datos
    String query =
        "UPDATE proximaactividad SET nombre = :nombre, lugar = :lugar, fechaInicial = :fechaInicial, fechaFinal = :fechaFinal  WHERE id = :id";

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
        : "Error al actualizar la actividad. Contacte al administrador del sistema.";
  }  
  
  public String eliminarActividad(Actividad actividad) {

    // Agrego los datos de la eliminación (nombreColumna/Valor)
    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", actividad.getId());

    // Armar la sentencia de actualización debase de datos
    String query = "DELETE FROM proximaactividad WHERE id = :id";

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
        : "Error al eliminar la actividad. Contacte al administrador del sistema.";
  }

  public List<Actividad> getUltimasActividades() {

    // Lista para retornar con los datos
    List<Actividad> actividades = new LinkedList<>();

    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM proximaactividad ORDER BY fechaInicial DESC LIMIT 0, 4 ");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      // Objeto en el que sera guardada la informacion del registro
      Actividad actividad = new Actividad();

      actividad.setId(sqlRowSet.getLong("id"));
      actividad.setNombre(sqlRowSet.getString("nombre"));
      actividad.setLugar(sqlRowSet.getString("lugar"));
      actividad.setFechaInicial(sqlRowSet.getDate("fechaInicial"));
      actividad.setFechaFinal(sqlRowSet.getDate("fechaFinal"));
      actividad.crearFechaFormato();
      cargarContenido(actividad);
      
      // Guarda el registro para ser retornado
      actividades.add(actividad);
    }

    // Retorna todos las actividades desde base de datos
    return actividades;
 
  }

  private void cargarContenido(Actividad actividad) {

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("id", actividad.getId());
    map.addValue("tipo", Constantes.ACTIVIDAD);
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery( " SELECT    contenido.id                    idContenido,            "
                                                  + "           contenido.contenido             contenido,              "
                                                  + "           contenido.TipoContenido_id      tipoContenido          "                                                  
                                                  + "   FROM    proximaactividad                                            "
                                                  + "INNER JOIN contenido  ON contenido.asociacion = proximaactividad.id "
                                                  + "WHERE proximaactividad.id = :id "
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
      actividad.setContenido(contenido);
    }
  }  
  
}
