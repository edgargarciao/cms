package co.ufps.edu.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import co.ufps.edu.bd.SpringDbMgr;

/**
 * Clase que permite acceder a la capa de datos en el entorno de tipos de contenido.
 * @author ufps
 *
 */
public class TipoContenidoDao {

  private SpringDbMgr springDbMgr;

  public TipoContenidoDao() {
    springDbMgr = new SpringDbMgr();
  }

  /**
   * Metodo que consulta en base de datos todas las actividades existentes y las devuelve
   * ordenadamente
   * 
   * @return Una lista con todas las actividades
   */
  public Map<Long,String> getContenidos() {

    // Lista para retornar con los datos
    Map<Long,String> mapaDeTiposDeContenido = new HashMap<>();
    
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet = springDbMgr.executeQuery(" SELECT * FROM tipocontenido");

    // Recorre cada registro obtenido de base de datos
    while (sqlRowSet.next()) {
      mapaDeTiposDeContenido.put(sqlRowSet.getLong("id"), sqlRowSet.getString("nombre"));
    }

    // Retorna todos las actividades desde base de datos
    return mapaDeTiposDeContenido;
  }

}
