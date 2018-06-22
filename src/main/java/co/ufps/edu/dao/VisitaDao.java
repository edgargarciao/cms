package co.ufps.edu.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import co.ufps.edu.bd.SpringDbMgr;
import co.ufps.edu.dto.Actividad;
import co.ufps.edu.dto.Visita;
import co.ufps.edu.util.ImagenUtil;

/**
 * Clase que permite acceder a la capa de datos en el entorno de actividades.
 * @author ufps
 *
 */
@Component
public class VisitaDao {

  private SpringDbMgr springDbMgr;

  public VisitaDao() {
    springDbMgr = new SpringDbMgr();
 
  }

  /**
   * Método que obtiene el numero de actividades guardadas en base de datos.
   * @return La cantidad de actividades registradas.
   */
  public int getCantidadVisitas() {
    int cant = 0;
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet =
        springDbMgr.executeQuery(" SELECT COUNT(*) cantidad FROM visita");

    if (sqlRowSet.next()) {
      cant = sqlRowSet.getInt("cantidad");
    }
    return cant;
  }

  /**
   * Método que obtiene el numero de actividades guardadas en base de datos.
   * @return La cantidad de actividades registradas.
   */
  public int getCantidadVisitasMes() {
    int cant = 0;
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet =
        springDbMgr.executeQuery(" SELECT count(*) cantidad FROM visita WHERE MONTH(fecha) = MONTH(CURRENT_DATE()) AND YEAR(fecha) = YEAR(CURRENT_DATE())");

    if (sqlRowSet.next()) {
      cant = sqlRowSet.getInt("cantidad");
    }
    return cant;
  }
  
  /**
   * Método que obtiene el numero de actividades guardadas en base de datos.
   * @return La cantidad de actividades registradas.
   */
  public int getCantidadVisitasDia() {
    int cant = 0;
    // Consulta para realizar en base de datos
    SqlRowSet sqlRowSet =
        springDbMgr.executeQuery(" SELECT count(*) cantidad FROM visita WHERE DAY(fecha) = DAY(CURRENT_DATE())");

    if (sqlRowSet.next()) {
      cant = sqlRowSet.getInt("cantidad");
    }
    return cant;
  }

  @Async
  public String registrarVisita(Visita visita) {
    // Agrego los datos del registro (nombreColumna/Valor)

    MapSqlParameterSource map = new MapSqlParameterSource();
    map.addValue("ip", visita.getIp());
    
    

    // Armar la sentencia de actualización debase de datos
    String query =
        "INSERT INTO visita(ip,fecha) VALUES(:ip,NOW())";

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
        : "Error al registrar la actividad. Contacte al administrador del sistema.";
  }  


  
  
}
